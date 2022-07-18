package br.com.passwordkeeper.domain.result

sealed class CreateUserResult {
    object Success : CreateUserResult()
    object ErrorWeakPassword: CreateUserResult()
    object ErrorEmailMalformed: CreateUserResult()
    object ErrorEmailAlreadyExists: CreateUserResult()
    object ErrorUnknown: CreateUserResult()
}