package br.com.passwordkeeper.domain.repository

sealed class CreateUserRepositoryResult {
    object Success : CreateUserRepositoryResult()
    object ErrorWeakPassword: CreateUserRepositoryResult()
    object ErrorEmailMalformed: CreateUserRepositoryResult()
    object ErrorEmailAlreadyExists: CreateUserRepositoryResult()
    object ErrorUnknown: CreateUserRepositoryResult()
}