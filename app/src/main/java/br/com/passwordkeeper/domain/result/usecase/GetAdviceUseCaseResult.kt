package br.com.passwordkeeper.domain.result.usecase

import br.com.passwordkeeper.domain.model.AdviceView

sealed class GetAdviceUseCaseResult {
    data class Success(val adviceView: AdviceView) : GetAdviceUseCaseResult()
    object ErrorUnknown : GetAdviceUseCaseResult()
    object SuccessAdviceWithoutMessage : GetAdviceUseCaseResult()
}