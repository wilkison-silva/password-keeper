package br.com.passwordkeeper.presentation.features

import br.com.passwordkeeper.presentation.model.UserView

sealed class CurrentUserState {
    data class Success(val userView: UserView) : CurrentUserState()
    object ErrorUnknown: CurrentUserState()
    object EmptyState: CurrentUserState()
}