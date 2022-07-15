package br.com.passwordkeeper.domain.result

sealed class FirebaseAuthCreateUserResult {
    object Success : FirebaseAuthCreateUserResult()
    object ErrorWeakPassword: FirebaseAuthCreateUserResult()
    object ErrorEmailMalformed: FirebaseAuthCreateUserResult()
    object ErrorEmailAlreadyExists: FirebaseAuthCreateUserResult()
}