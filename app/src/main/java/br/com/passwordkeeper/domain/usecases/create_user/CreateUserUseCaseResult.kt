package br.com.passwordkeeper.domain.usecases.create_user

sealed class CreateUserUseCaseResult {
    object Success : CreateUserUseCaseResult()
    object ErrorWeakPassword: CreateUserUseCaseResult()
    object ErrorEmailMalformed: CreateUserUseCaseResult()
    object ErrorEmailAlreadyExists: CreateUserUseCaseResult()
    object ErrorUnknown: CreateUserUseCaseResult()
}