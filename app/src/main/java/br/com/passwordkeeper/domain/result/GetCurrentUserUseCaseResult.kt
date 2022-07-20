package br.com.passwordkeeper.domain.result

import br.com.passwordkeeper.domain.model.UserView

sealed class GetCurrentUserUseCaseResult {
    data class Success(val userView: UserView) : GetCurrentUserUseCaseResult()
    object ErrorUnknown: GetCurrentUserUseCaseResult()
}