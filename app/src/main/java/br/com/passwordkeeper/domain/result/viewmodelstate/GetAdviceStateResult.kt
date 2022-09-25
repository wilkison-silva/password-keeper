package br.com.passwordkeeper.domain.result.viewmodelstate

import br.com.passwordkeeper.presentation.model.AdviceView

sealed class GetAdviceStateResult {
    data class Success(val adviceView: AdviceView) : GetAdviceStateResult()
    object SuccessWithoutMessage: GetAdviceStateResult()
    object ErrorUnknown: GetAdviceStateResult()
    object Loading: GetAdviceStateResult()
}