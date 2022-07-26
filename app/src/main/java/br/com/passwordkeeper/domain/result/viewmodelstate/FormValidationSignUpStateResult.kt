package br.com.passwordkeeper.domain.result.viewmodelstate

sealed class FormValidationSignUpStateResult {
    data class Success(
        val name: String,
        val email: String,
        val password: String,
    ) : FormValidationSignUpStateResult()
    object ErrorNameIsBlank : FormValidationSignUpStateResult()
    object ErrorEmailIsBlank : FormValidationSignUpStateResult()
    object ErrorEmailMalFormed : FormValidationSignUpStateResult()
    object ErrorPasswordIsBlank : FormValidationSignUpStateResult()
    object ErrorPasswordTooWeak : FormValidationSignUpStateResult()
    object ErrorPasswordsDoNotMatch : FormValidationSignUpStateResult()
}