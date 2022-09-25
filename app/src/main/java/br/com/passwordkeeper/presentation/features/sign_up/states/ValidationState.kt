package br.com.passwordkeeper.presentation.features.sign_up.states

sealed class ValidationState {
    object Success : ValidationState()
    object Error : ValidationState()
}