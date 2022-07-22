package br.com.passwordkeeper.domain.result.usecase

sealed class CreateUserUseCaseResult {
    object Success : CreateUserUseCaseResult()
    object ErrorWeakPassword: CreateUserUseCaseResult()
    object ErrorEmailMalformed: CreateUserUseCaseResult()
    object ErrorEmailAlreadyExists: CreateUserUseCaseResult()
    object ErrorUnknown: CreateUserUseCaseResult()
}