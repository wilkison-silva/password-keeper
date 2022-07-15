package br.com.passwordkeeper.data.repository

import br.com.passwordkeeper.data.repository.source.web.result.FirebaseAuthCreateUserResult
import br.com.passwordkeeper.data.repository.source.web.result.FirebaseAuthGetCurrentUserResult
import br.com.passwordkeeper.data.repository.source.web.result.SignInResult

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