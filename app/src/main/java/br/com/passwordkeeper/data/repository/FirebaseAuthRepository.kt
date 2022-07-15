package br.com.passwordkeeper.data.repository

import br.com.passwordkeeper.domain.result.FirebaseAuthCreateUserResult
import br.com.passwordkeeper.domain.result.FirebaseAuthGetCurrentUserResult
import br.com.passwordkeeper.domain.result.SignInResult

interface FirebaseAuthRepository {

    fun signIn(
        email: String,
        password: String,
        callbackResult: (signInResult: SignInResult) -> Unit
    )

    fun signOut()

    fun createUser(
        email: String,
        password: String,
        callbackResult: (firebaseAuthCreateUserResult: FirebaseAuthCreateUserResult) -> Unit
    )

    fun getCurrentUser(
        callbackResult: (firebaseAuthGetCurrentUserResult: FirebaseAuthGetCurrentUserResult) -> Unit
    )

}