package br.com.passwordkeeper.domain.result

sealed class SignOutUseCaseResult {
    object Success : SignOutUseCaseResult()
    object ErrorUnknown: SignOutUseCaseResult()
}