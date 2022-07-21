package br.com.passwordkeeper.data.repository

import br.com.passwordkeeper.domain.model.UserResponse
import br.com.passwordkeeper.domain.result.CreateUserRepositoryResult
import br.com.passwordkeeper.domain.result.GetCurrentUserRepositoryResult
import br.com.passwordkeeper.domain.result.SignInRepositoryResult
import br.com.passwordkeeper.domain.result.SignOutRepositoryResult
import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseAuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth,
    private val fireStore: FirebaseFirestore
) : AuthRepository {

    override suspend fun signIn(email: String, password: String): SignInRepositoryResult {

        return try {
            val response = firebaseAuth
                .signInWithEmailAndPassword(email, password).await()
            response.user?.email?.let { emailUser: String ->
                //TODO get name from another api from firebase
                val userResponse = UserResponse(
                    email = emailUser,
                    name = "Nome de Teste"
                )
                SignInRepositoryResult.Success(userResponse.convertToUser())
            } ?: SignInRepositoryResult.ErrorUserNotFound
        } catch (exception: Exception) {
            when (exception) {
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

    override suspend fun createUser(email: String, password: String): CreateUserRepositoryResult {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
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
        return firebaseAuth.currentUser?.email?.let { emailUser: String ->
            //TODO get name from another api from firebase
            val userResponse = UserResponse(
                email = emailUser,
                name = "Nome de Teste"
            )
            GetCurrentUserRepositoryResult.Success(userResponse.convertToUser())
        } ?: GetCurrentUserRepositoryResult.ErrorNoUserRepositoryFound
    }

}