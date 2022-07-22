package br.com.passwordkeeper.domain.result.viewmodelstate

import br.com.passwordkeeper.domain.model.AdviceDomain

sealed class GetAdviceStateResult {
    data class Success(val adviceDomain: AdviceDomain) : GetAdviceStateResult()
    object SuccessWithoutMessage: GetAdviceStateResult()
    object ErrorUnknown: GetAdviceStateResult()
    object Loading: GetAdviceStateResult()
}