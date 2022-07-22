package br.com.passwordkeeper.data.repository

import br.com.passwordkeeper.domain.model.UserResponse
import br.com.passwordkeeper.domain.result.CreateUserRepositoryResult
import br.com.passwordkeeper.domain.result.GetCurrentUserRepositoryResult
import br.com.passwordkeeper.domain.result.SignInRepositoryResult
import br.com.passwordkeeper.domain.result.SignOutRepositoryResult
import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await

private const val COLLECTION_USERS = "users"

class FirebaseAuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth,
    private val fireStore: FirebaseFirestore
) : AuthRepository {

    override suspend fun signIn(email: String, password: String): SignInRepositoryResult {
        try {
            val response = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            response.user?.email?.let { emailUser: String ->
                val userResponse = getUserData(emailUser)
                if (userResponse.email.isNotBlank()) {
                    return SignInRepositoryResult.Success(userResponse.convertToUser())
                }
                return SignInRepositoryResult.ErrorUserNotFound
            } ?: return SignInRepositoryResult.ErrorUserNotFound
        } catch (exception: Exception) {
            return when (exception) {
                is FirebaseAuthInvalidUserException,
                is FirebaseAuthInvalidCredentialsException ->
                    SignInRepositoryResult.ErrorEmailOrPasswordIncorrect
                else -> SignInRepositoryResult.ErrorUnknown
            }
        }
    }

    override suspend fun signOut(): SignOutRepositoryResult {
        return try {
            firebaseAuth.signOut()
            SignOutRepositoryResult.Success
        } catch (exception: Exception) {
            SignOutRepositoryResult.ErrorUnknown
        }
    }

    override suspend fun createUser(
        name: String,
        email: String,
        password: String
    ): CreateUserRepositoryResult {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            setUserData(email, name)
            CreateUserRepositoryResult.Success
        } catch (exception: Exception) {
            when (exception) {
                is FirebaseAuthWeakPasswordException -> CreateUserRepositoryResult.ErrorWeakPassword
                is FirebaseAuthInvalidCredentialsException -> CreateUserRepositoryResult.ErrorEmailMalformed
                is FirebaseAuthUserCollisionException -> CreateUserRepositoryResult.ErrorEmailAlreadyExists
                else -> CreateUserRepositoryResult.ErrorUnknown
            }
        }
    }

    override suspend fun getCurrentUser(): GetCurrentUserRepositoryResult {
        firebaseAuth.currentUser?.email?.let { emailUser: String ->
            val userResponse = getUserData(emailUser)
            if (userResponse.email.isNotBlank()) {
                return GetCurrentUserRepositoryResult.Success(userResponse.convertToUser())
            }
            return GetCurrentUserRepositoryResult.ErrorNoUserRepositoryFound
        } ?: return GetCurrentUserRepositoryResult.ErrorNoUserRepositoryFound
    }

    private suspend fun setUserData(emailUser: String, name: String) {
        try {
            fireStore.collection(COLLECTION_USERS)
                .document(emailUser).set(
                    UserResponse(
                        email = emailUser,
                        name = name
                    )
                ).await()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }

    private suspend fun getUserData(emailUser: String): UserResponse {
        try {
            val documentSnapshot = fireStore
                .collection(COLLECTION_USERS)
                .document(emailUser).get().await()
            documentSnapshot?.toObject<UserResponse>()?.let { userResponse: UserResponse ->
                return userResponse
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        return UserResponse()
    }

}