package br.com.passwordkeeper.domain.result

import br.com.passwordkeeper.domain.model.AdviceResponse

sealed class GetAdviceRepositoryResult {
    data class Success(val adviceResponse: AdviceResponse?) : GetAdviceRepositoryResult()
    object ErrorUnknown: GetAdviceRepositoryResult()
}