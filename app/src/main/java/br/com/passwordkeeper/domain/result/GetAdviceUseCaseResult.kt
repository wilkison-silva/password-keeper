package br.com.passwordkeeper.domain.result

import br.com.passwordkeeper.domain.model.Advice

sealed class GetAdviceUseCaseResult {
    data class Success(val advice: Advice) : GetAdviceUseCaseResult()
    object ErrorUnknown: GetAdviceUseCaseResult()
    object SuccessAdviceWithoutMessage: GetAdviceUseCaseResult()
}