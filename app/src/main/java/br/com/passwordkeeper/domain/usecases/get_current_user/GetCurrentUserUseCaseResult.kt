package br.com.passwordkeeper.domain.usecases.get_current_user

import br.com.passwordkeeper.presentation.model.UserView

sealed class GetCurrentUserUseCaseResult {
    data class Success(val userView: UserView) : GetCurrentUserUseCaseResult()
    object ErrorUnknown: GetCurrentUserUseCaseResult()
}