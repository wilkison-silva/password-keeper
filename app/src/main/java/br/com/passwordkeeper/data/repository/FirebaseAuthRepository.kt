package br.com.passwordkeeper.data.repository

import br.com.passwordkeeper.data.source.web.result.FirebaseAuthCreateUserResult
import br.com.passwordkeeper.data.source.web.result.FirebaseAuthGetCurrentUserResult
import br.com.passwordkeeper.data.source.web.result.FirebaseAuthSignInResult

interface FirebaseAuthRepository {

    fun signIn(
        email: String,
        password: String,
        callbackResult: (firebaseAuthSignInResult: FirebaseAuthSignInResult) -> Unit
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