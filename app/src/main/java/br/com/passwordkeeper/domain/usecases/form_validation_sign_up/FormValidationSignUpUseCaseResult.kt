package br.com.passwordkeeper.domain.usecases.form_validation_sign_up

sealed class FormValidationSignUpUseCaseResult {
    object Success : FormValidationSignUpUseCaseResult()
    object ErrorNameIsBlank: FormValidationSignUpUseCaseResult()
    object ErrorEmailIsBlank: FormValidationSignUpUseCaseResult()
    object ErrorEmailMalFormed: FormValidationSignUpUseCaseResult()
    object ErrorPasswordIsBlank: FormValidationSignUpUseCaseResult()
    object ErrorPasswordTooWeak: FormValidationSignUpUseCaseResult()
    object ErrorPasswordsDoNotMatch: FormValidationSignUpUseCaseResult()
}