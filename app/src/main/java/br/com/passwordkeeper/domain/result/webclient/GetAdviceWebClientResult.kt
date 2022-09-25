package br.com.passwordkeeper.domain.result.webclient

import br.com.passwordkeeper.data.model.AdviceData

sealed class GetAdviceWebClientResult {
    data class Success(val adviceData: AdviceData?) : GetAdviceWebClientResult()
    object ErrorUnknown: GetAdviceWebClientResult()
}