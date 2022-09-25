package br.com.passwordkeeper.domain.result.viewmodelstate

import br.com.passwordkeeper.presentation.model.UserView

sealed class CurrentUserState {
    data class Success(val userView: UserView) : CurrentUserState()
    object ErrorUnknown: CurrentUserState()
}