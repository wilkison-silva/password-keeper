package br.com.passwordkeeper.data.source.web

import br.com.passwordkeeper.domain.repository.FirebaseAuthRepository
import br.com.passwordkeeper.data.source.web.result.FirebaseAuthCreateUserResult
import br.com.passwordkeeper.data.source.web.result.FirebaseAuthGetCurrentUserResult
import br.com.passwordkeeper.data.source.web.result.FirebaseAuthSignInResult
import com.google.firebase.auth.*

class FirebaseAuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth
) : FirebaseAuthRepository {

    override fun signIn(
        email: String,
        password: String,
        callbackResult: (firebaseAuthSignInResult: FirebaseAuthSignInResult) -> Unit
    ) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                it.user?.let { currentUser: FirebaseUser ->
                    callbackResult(FirebaseAuthSignInResult.Success(currentUser))
                } ?: callbackResult(FirebaseAuthSignInResult.ErrorUserNotFound)
            }
            .addOnFailureListener {
                when (it) {
                    is FirebaseAuthInvalidUserException,
                    is FirebaseAuthInvalidCredentialsException -> callbackResult(
                        FirebaseAuthSignInResult.ErrorEmailOrPasswordIncorrect
                    )
                    else -> callbackResult(FirebaseAuthSignInResult.ErrorUnknown)
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