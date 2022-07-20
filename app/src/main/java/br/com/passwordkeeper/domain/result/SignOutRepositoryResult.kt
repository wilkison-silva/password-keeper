package br.com.passwordkeeper.domain.result

sealed class SignOutRepositoryResult {
    object Success : SignOutRepositoryResult()
    object ErrorUnknown: SignOutRepositoryResult()
}