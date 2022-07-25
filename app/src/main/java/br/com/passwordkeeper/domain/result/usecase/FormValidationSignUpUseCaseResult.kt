package br.com.passwordkeeper.domain.result.usecase

sealed class FormValidationSignUpUseCaseResult {
    object Success : FormValidationSignUpUseCaseResult()
    object ErrorNameIsBlank: FormValidationSignUpUseCaseResult()
    object ErrorEmailIsBlank: FormValidationSignUpUseCaseResult()
    object ErrorEmailMalFormed: FormValidationSignUpUseCaseResult()
    object ErrorPasswordIsBlank: FormValidationSignUpUseCaseResult()
    object ErrorPasswordTooWeak: FormValidationSignUpUseCaseResult()
    object ErrorPasswordsDoNotMatch: FormValidationSignUpUseCaseResult()
}