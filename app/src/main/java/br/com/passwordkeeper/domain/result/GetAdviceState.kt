package br.com.passwordkeeper.domain.result

import br.com.passwordkeeper.domain.model.Advice

sealed class GetAdviceState {
    data class Success(val advice: Advice) : GetAdviceState()
    object SuccessWithoutMessage: GetAdviceState()
    object ErrorUnknown: GetAdviceState()
}