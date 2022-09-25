package br.com.passwordkeeper.domain.usecases.form_validation_sign_in

sealed class FormValidationSignInUseCaseResult {
    object Success : FormValidationSignInUseCaseResult()
    object ErrorEmailIsBlank: FormValidationSignInUseCaseResult()
    object ErrorEmailMalFormed: FormValidationSignInUseCaseResult()
    object ErrorPasswordIsBlank: FormValidationSignInUseCaseResult()
}