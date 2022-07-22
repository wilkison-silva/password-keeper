package br.com.passwordkeeper.domain.result.repository

sealed class CreateUserRepositoryResult {
    object Success : CreateUserRepositoryResult()
    object ErrorWeakPassword: CreateUserRepositoryResult()
    object ErrorEmailMalformed: CreateUserRepositoryResult()
    object ErrorEmailAlreadyExists: CreateUserRepositoryResult()
    object ErrorUnknown: CreateUserRepositoryResult()
}