package br.com.passwordkeeper.domain.result.repository

sealed class SignOutRepositoryResult {
    object Success : SignOutRepositoryResult()
    object ErrorUnknown: SignOutRepositoryResult()
}