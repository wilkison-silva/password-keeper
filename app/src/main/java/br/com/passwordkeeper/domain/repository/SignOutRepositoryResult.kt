package br.com.passwordkeeper.domain.repository

sealed class SignOutRepositoryResult {
    object Success : SignOutRepositoryResult()
    object ErrorUnknown: SignOutRepositoryResult()
}