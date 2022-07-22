package br.com.passwordkeeper.domain.result.usecase

sealed class ValidationFormSignUpUseCaseResult {
    object Success : ValidationFormSignUpUseCaseResult()
    object ErrorNameIsBlank: ValidationFormSignUpUseCaseResult()
    object ErrorEmailIsBlank: ValidationFormSignUpUseCaseResult()
    object ErrorEmailMalFormed: ValidationFormSignUpUseCaseResult()
    object ErrorPasswordIsBlank: ValidationFormSignUpUseCaseResult()
    object ErrorPasswordTooWeak: ValidationFormSignUpUseCaseResult()
}