package br.com.passwordkeeper.domain.result

import br.com.passwordkeeper.domain.model.Advice

sealed class GetAdviceStateResult {
    data class Success(val advice: Advice) : GetAdviceStateResult()
    object SuccessWithoutMessage: GetAdviceStateResult()
    object ErrorUnknown: GetAdviceStateResult()
    object Loading: GetAdviceStateResult()
}