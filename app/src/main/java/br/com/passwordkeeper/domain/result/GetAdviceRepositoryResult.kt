package br.com.passwordkeeper.domain.result

import br.com.passwordkeeper.data.source.web.model.AdviceResponse

sealed class GetAdviceRepositoryResult {
    data class Success(val adviceResponse: AdviceResponse?) : GetAdviceRepositoryResult()
    object ErrorUnknown: GetAdviceRepositoryResult()
}