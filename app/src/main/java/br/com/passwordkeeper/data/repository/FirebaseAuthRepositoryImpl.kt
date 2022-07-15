package br.com.passwordkeeper.data.repository

import br.com.passwordkeeper.domain.result.FirebaseAuthCreateUserResult
import br.com.passwordkeeper.domain.result.FirebaseAuthGetCurrentUserResult
import br.com.passwordkeeper.domain.result.SignInResult
import com.google.firebase.auth.*

class FirebaseAuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth
) : FirebaseAuthRepository {

    override fun signIn(
        email: String,
        password: String,
        callbackResult: (signInResult: SignInResult) -> Unit
    ) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                it.user?.email?.let { email: String ->
                    callbackResult(SignInResult.Success(email))
                } ?: callbackResult(SignInResult.ErrorUserNotFound)
            }
            .addOnFailureListener {
                when (it) {
                    is FirebaseAuthInvalidUserException,
                    is FirebaseAuthInvalidCredentialsException -> callbackResult(
                        SignInResult.ErrorEmailOrPasswordIncorrect
                    )
                    else -> callbackResult(SignInResult.ErrorUnknown)
                }
            }
    }

    override fun signOut() {
        firebaseAuth.signOut()
    }

    override fun createUser(
        email: String,
        password: String,
        callbackResult: (firebaseAuthCreateUserResult: FirebaseAuthCreateUserResult) -> Unit
    ) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                callbackResult(FirebaseAuthCreateUserResult.Success)
            }
            .addOnFailureListener {
                when (it) {
                    is FirebaseAuthWeakPasswordException -> {
                        callbackResult(FirebaseAuthCreateUserResult.ErrorWeakPassword)
                    }
                    is FirebaseAuthInvalidCredentialsException -> {
                        callbackResult(FirebaseAuthCreateUserResult.ErrorEmailMalformed)
                    }
                    is FirebaseAuthUserCollisionException -> {
                        callbackResult(FirebaseAuthCreateUserResult.ErrorEmailAlreadyExists)
                    }
                }
            }
    }

    override fun getCurrentUser(
        callbackResult: (
            firebaseAuthGetCurrentUserResult: FirebaseAuthGetCurrentUserResult
        ) -> Unit
    ) {
        firebaseAuth.currentUser?.let { currentUser: FirebaseUser ->
            callbackResult(FirebaseAuthGetCurrentUserResult.Success(currentUser))
        } ?: callbackResult(FirebaseAuthGetCurrentUserResult.ErrorNoUserFound)
    }

}