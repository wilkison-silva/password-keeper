package br.com.passwordkeeper.domain.result.usecase

import br.com.passwordkeeper.presentation.model.UserView

sealed class GetCurrentUserUseCaseResult {
    data class Success(val userView: UserView) : GetCurrentUserUseCaseResult()
    object ErrorUnknown: GetCurrentUserUseCaseResult()
}