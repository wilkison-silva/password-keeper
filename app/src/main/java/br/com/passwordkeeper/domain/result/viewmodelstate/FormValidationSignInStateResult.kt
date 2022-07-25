package br.com.passwordkeeper.domain.result.viewmodelstate

sealed class FormValidationSignInStateResult {
    data class Success(val email: String, val password: String) : FormValidationSignInStateResult()
    object ErrorEmailIsBlank: FormValidationSignInStateResult()
    object ErrorEmailMalFormed: FormValidationSignInStateResult()
    object ErrorPasswordIsBlank: FormValidationSignInStateResult()
}