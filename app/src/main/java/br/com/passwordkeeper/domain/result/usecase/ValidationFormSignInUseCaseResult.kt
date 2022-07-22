package br.com.passwordkeeper.domain.result.usecase

sealed class ValidationFormSignInUseCaseResult {
    object Success : ValidationFormSignInUseCaseResult()
    object ErrorEmailIsBlank: ValidationFormSignInUseCaseResult()
    object ErrorEmailMalFormed: ValidationFormSignInUseCaseResult()
    object ErrorPasswordIsBlank: ValidationFormSignInUseCaseResult()
}