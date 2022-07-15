package br.com.passwordkeeper.data.repository.source.web.result

sealed class FirebaseAuthCreateUserResult {
    object Success : FirebaseAuthCreateUserResult()
    object ErrorWeakPassword: FirebaseAuthCreateUserResult()
    object ErrorEmailMalformed: FirebaseAuthCreateUserResult()
    object ErrorEmailAlreadyExists: FirebaseAuthCreateUserResult()
}