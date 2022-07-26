package br.com.passwordkeeper.domain.result.viewmodelstate

sealed class CreateUserStateResult {
    object Success : CreateUserStateResult()
    object ErrorWeakPassword: CreateUserStateResult()
    object ErrorEmailMalformed: CreateUserStateResult()
    object ErrorEmailAlreadyExists: CreateUserStateResult()
    object ErrorUnknown: CreateUserStateResult()
}