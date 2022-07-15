package br.com.passwordkeeper.data.datasource.repository

import br.com.passwordkeeper.data.datasource.web.result.FirebaseAuthCreateUserResult
import br.com.passwordkeeper.data.datasource.web.result.FirebaseAuthGetCurrentUserResult
import br.com.passwordkeeper.data.datasource.web.result.FirebaseAuthSignInResult

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