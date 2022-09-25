package br.com.passwordkeeper.domain.repository

import br.com.passwordkeeper.data.model.AdviceData

sealed class GetAdviceWebClientResult {
    data class Success(val adviceData: AdviceData?) : GetAdviceWebClientResult()
    object ErrorUnknown: GetAdviceWebClientResult()
}