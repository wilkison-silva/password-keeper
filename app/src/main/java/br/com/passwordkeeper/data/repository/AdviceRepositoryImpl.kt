package br.com.passwordkeeper.data.repository

import br.com.passwordkeeper.data.source.web.AdviceWebClient
import br.com.passwordkeeper.domain.mapper.AdviceDataMapper
import br.com.passwordkeeper.domain.result.repository.GetAdviceRepositoryResult
import br.com.passwordkeeper.domain.result.webclient.GetAdviceWebClientResult

class AdviceRepositoryImpl(
    private val adviceWebClient: AdviceWebClient,
    private val adviceDataMapper: AdviceDataMapper
) : AdviceRepository {

    override suspend fun getAdvice(): GetAdviceRepositoryResult {
        when (val getAdviceWebClientResult = adviceWebClient.getAdvice()) {
            is GetAdviceWebClientResult.Success -> {
                getAdviceWebClientResult.adviceData?.let { adviceData ->
                    return GetAdviceRepositoryResult.Success(adviceDataMapper.transform(adviceData))
                } ?: return GetAdviceRepositoryResult.ErrorUnknown
            }
            is GetAdviceWebClientResult.ErrorUnknown -> {
                return GetAdviceRepositoryResult.ErrorUnknown
            }
        }
    }
}
