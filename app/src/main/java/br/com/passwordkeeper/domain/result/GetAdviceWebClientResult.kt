package br.com.passwordkeeper.domain.result

import br.com.passwordkeeper.data.source.web.model.AdviceResponse

sealed class GetAdviceWebClientResult {
    data class Success(val adviceResponse: AdviceResponse?) : GetAdviceWebClientResult()
    object ErrorUnknown: GetAdviceWebClientResult()
}