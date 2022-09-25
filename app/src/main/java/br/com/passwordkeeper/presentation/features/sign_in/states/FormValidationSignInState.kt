package br.com.passwordkeeper.presentation.features.sign_in.states

sealed class FormValidationSignInState {
    data class Success(val email: String, val password: String) : FormValidationSignInState()
    object ErrorEmailIsBlank: FormValidationSignInState()
    object ErrorEmailMalFormed: FormValidationSignInState()
    object ErrorPasswordIsBlank: FormValidationSignInState()
    object EmptyState: FormValidationSignInState()
}