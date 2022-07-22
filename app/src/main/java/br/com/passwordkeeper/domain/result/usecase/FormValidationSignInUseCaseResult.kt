package br.com.passwordkeeper.domain.result.usecase

sealed class FormValidationSignInUseCaseResult {
    object Success : FormValidationSignInUseCaseResult()
    object ErrorEmailIsBlank: FormValidationSignInUseCaseResult()
    object ErrorEmailMalFormed: FormValidationSignInUseCaseResult()
    object ErrorPasswordIsBlank: FormValidationSignInUseCaseResult()
}