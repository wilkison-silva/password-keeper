package br.com.passwordkeeper.presentation.features.sign_in.states

import br.com.passwordkeeper.presentation.model.UserView

sealed class SignInState {
    data class Success(val userView: UserView) : SignInState()
    object ErrorEmailOrPasswordWrong: SignInState()
    object ErrorUnknown: SignInState()
    object EmptyState: SignInState()
    object Loading: SignInState()
}