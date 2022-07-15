package br.com.passwordkeeper.data.datasource.web.result

sealed class FirebaseAuthCreateUserResult {
    object Success : FirebaseAuthCreateUserResult()
    object ErrorWeakPassword: FirebaseAuthCreateUserResult()
    object ErrorEmailMalformed: FirebaseAuthCreateUserResult()
    object ErrorEmailAlreadyExists: FirebaseAuthCreateUserResult()
}