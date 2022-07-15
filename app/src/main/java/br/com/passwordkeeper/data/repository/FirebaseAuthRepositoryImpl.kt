package br.com.passwordkeeper.data.repository

import br.com.passwordkeeper.domain.result.CreateUserResult
import br.com.passwordkeeper.domain.result.GetCurrentUserResult
import br.com.passwordkeeper.domain.result.SignInResult
import br.com.passwordkeeper.domain.result.SignOutResult
import com.google.firebase.auth.*
import kotlinx.coroutines.tasks.await

class FirebaseAuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    override suspend fun signIn(email: String, password: String): SignInResult {

        return try {
            val response = firebaseAuth
                .signInWithEmailAndPassword(email, password).await()
            response.user?.email?.let {
                SignInResult.Success(it)
            } ?: SignInResult.ErrorUserNotFound
        } catch (exception: Exception) {
            when (exception) {
                is FirebaseAuthInvalidUserException,
                is FirebaseAuthInvalidCredentialsException ->
                    SignInResult.ErrorEmailOrPasswordIncorrect
                else -> SignInResult.ErrorUnknown
            }
        }
    }

    override suspend fun signOut(): SignOutResult {
        return try {
            firebaseAuth.signOut()
            SignOutResult.Success
        } catch (exception: Exception) {
            SignOutResult.ErrorUnknown
        }
    }

    override suspend fun createUser(email: String, password: String): CreateUserResult {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            CreateUserResult.Success
        } catch (exception: Exception) {
            when (exception) {
                is FirebaseAuthWeakPasswordException -> CreateUserResult.ErrorWeakPassword
                is FirebaseAuthInvalidCredentialsException -> CreateUserResult.ErrorEmailMalformed
                is FirebaseAuthUserCollisionException -> CreateUserResult.ErrorEmailAlreadyExists
                else -> CreateUserResult.ErrorUnknown
            }
        }
    }

    override suspend fun getCurrentUser(): GetCurrentUserResult {
        return firebaseAuth.currentUser?.email?.let { emailUser: String ->
            GetCurrentUserResult.Success(emailUser)
        } ?: GetCurrentUserResult.ErrorNoUserFound
    }

}