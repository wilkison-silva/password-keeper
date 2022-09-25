package br.com.passwordkeeper.domain.usecases.sign_out

sealed class SignOutUseCaseResult {
    object Success : SignOutUseCaseResult()
    object ErrorUnknown: SignOutUseCaseResult()
}