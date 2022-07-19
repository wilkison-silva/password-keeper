package br.com.passwordkeeper.domain.result

import br.com.passwordkeeper.domain.model.AdviceResponse

sealed class GetAdviceWebClientResult {
    data class Success(val adviceResponse: AdviceResponse?) : GetAdviceWebClientResult()
    object ErrorUnknown: GetAdviceWebClientResult()
}