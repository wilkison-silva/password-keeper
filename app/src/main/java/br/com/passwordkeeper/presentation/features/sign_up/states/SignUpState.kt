package br.com.passwordkeeper.presentation.features.sign_up.states

sealed class SignUpState {
    object Success : SignUpState()
    object ErrorUnknown: SignUpState()
}