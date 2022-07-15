package br.com.passwordkeeper.data.network.repository

import br.com.passwordkeeper.data.network.result.FirebaseAuthCreateUserResult
import br.com.passwordkeeper.data.network.result.FirebaseAuthSignInResult
import com.google.firebase.auth.*

class FirebaseAuthRepository(
    private val firebaseAuth: FirebaseAuth
) {

    fun signIn(
        email: String,
        password: String,
        callbackResult: (firebaseAuthSignInResult: FirebaseAuthSignInResult) -> Unit
    ) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                callbackResult(FirebaseAuthSignInResult.Success(it.user))
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

    fun signOut() {
        firebaseAuth.signOut()
    }

    fun createUser(
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

    fun getCurrentUser(): FirebaseUser? = firebaseAuth.currentUser

}