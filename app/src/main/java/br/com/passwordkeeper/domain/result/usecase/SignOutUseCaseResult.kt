package br.com.passwordkeeper.domain.result.usecase

sealed class SignOutUseCaseResult {
    object Success : SignOutUseCaseResult()
    object ErrorUnknown: SignOutUseCaseResult()
}