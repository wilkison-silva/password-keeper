package br.com.passwordkeeper.domain.usecases.get_advice

import br.com.passwordkeeper.presentation.model.AdviceView

sealed class GetAdviceUseCaseResult {
    data class Success(val adviceView: AdviceView) : GetAdviceUseCaseResult()
    object ErrorUnknown : GetAdviceUseCaseResult()
    object SuccessAdviceWithoutMessage : GetAdviceUseCaseResult()
}