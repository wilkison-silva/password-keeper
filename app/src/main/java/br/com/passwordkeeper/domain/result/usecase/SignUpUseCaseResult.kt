package br.com.passwordkeeper.domain.result.usecase

sealed class SignUpUseCaseResult {
    object Success : SignUpUseCaseResult()
    object ErrorUnknown: SignUpUseCaseResult()
}