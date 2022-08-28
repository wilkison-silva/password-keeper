package br.com.passwordkeeper.data.repository

import br.com.passwordkeeper.domain.mapper.UserDataMapper
import br.com.passwordkeeper.domain.mapper.UserFirestoreMapper
import br.com.passwordkeeper.domain.model.UserData
import br.com.passwordkeeper.domain.model.UserFirestore
import br.com.passwordkeeper.domain.result.repository.CreateUserRepositoryResult
import br.com.passwordkeeper.domain.result.repository.GetCurrentUserRepositoryResult
import br.com.passwordkeeper.domain.result.repository.SignInRepositoryResult
import br.com.passwordkeeper.domain.result.repository.SignOutRepositoryResult
import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await

class FirebaseAuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth,
    private val fireStore: FirebaseFirestore,
    private val userFirestoreMapper: UserFirestoreMapper,
    private val userDataMapper: UserDataMapper
) : AuthRepository {

    override suspend fun signIn(email: String, password: String): SignInRepositoryResult {
        try {
            val response = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            response.user?.email?.let { emailUser: String ->
                val userData = getUserData(emailUser)
                if (userData.email.isNotBlank()){
                    val userDomain = userDataMapper.transform(userData)
                    return SignInRepositoryResult.Success(userDomain)
                }
            }
        } catch (exception: Exception) {
            return when (exception) {
                is FirebaseAuthInvalidUserException,
                is FirebaseAuthInvalidCredentialsException ->
                    SignInRepositoryResult.ErrorEmailOrPasswordIncorrect
                else -> SignInRepositoryResult.ErrorUnknown
            }
        }
        return SignInRepositoryResult.ErrorUserNotFound
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
            createUserInFirebaseAuthentication(email, password)
            createUserInFirebaseFirestore(email, name)
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

    private suspend fun createUserInFirebaseAuthentication(
        email: String,
        password: String
    ) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).await()
    }

    override suspend fun getCurrentUser(): GetCurrentUserRepositoryResult {
        firebaseAuth.currentUser?.email?.let { emailUser: String ->
            val userData = getUserData(emailUser)
            if (userData.email.isNotBlank()) {
                val userDomain = userDataMapper.transform(userData)
                return GetCurrentUserRepositoryResult.Success(userDomain)
            }
            return GetCurrentUserRepositoryResult.ErrorNoUserRepositoryFound
        } ?: return GetCurrentUserRepositoryResult.ErrorNoUserRepositoryFound
    }

    private suspend fun createUserInFirebaseFirestore(emailUser: String, name: String) {
        try {
            fireStore.collection(COLLECTION_USERS)
                .document(emailUser).set(
                    UserFirestore(
                        email = emailUser,
                        name = name
                    )
                ).await()
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }

    private suspend fun getUserData(emailUser: String): UserData {
        try {
            val userDocumentSnapshot = fireStore
                .collection(COLLECTION_USERS)
                .document(emailUser).get().await()
            val userFirestore = userDocumentSnapshot?.toObject<UserFirestore>() as UserFirestore
            return userFirestoreMapper.transform(userFirestore)

        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        return UserData()
    }
}