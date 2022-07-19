package br.com.passwordkeeper.data.repository

import br.com.passwordkeeper.data.source.web.AdviceWebClient
import br.com.passwordkeeper.domain.result.GetAdviceRepositoryResult
import br.com.passwordkeeper.domain.result.GetAdviceWebClientResult

class AdviceRepositoryImpl(
    private val adviceWebClient: AdviceWebClient): AdviceRepository{

    override suspend fun getAdvice(): GetAdviceRepositoryResult {
        return when(val getAdviceWebClientResult = adviceWebClient.getAdvice()) {
            is GetAdviceWebClientResult.Success -> {
                GetAdviceRepositoryResult.Success(getAdviceWebClientResult.adviceResponse)
            }
            is GetAdviceWebClientResult.ErrorUnknown -> {
                GetAdviceRepositoryResult.ErrorUnknown
            }
        }
    }
}
