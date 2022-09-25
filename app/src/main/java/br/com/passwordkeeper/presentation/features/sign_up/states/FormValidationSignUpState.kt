package br.com.passwordkeeper.presentation.features.sign_up.states

sealed class FormValidationSignUpState {
    data class Success(
        val name: String,
        val email: String,
        val password: String,
    ) : FormValidationSignUpState()
    object ErrorNameIsBlank : FormValidationSignUpState()
    object ErrorEmailIsBlank : FormValidationSignUpState()
    object ErrorEmailMalFormed : FormValidationSignUpState()
    object ErrorPasswordIsBlank : FormValidationSignUpState()
    object ErrorPasswordTooWeak : FormValidationSignUpState()
    object ErrorPasswordsDoNotMatch : FormValidationSignUpState()
    object EmptyState: FormValidationSignUpState()
}