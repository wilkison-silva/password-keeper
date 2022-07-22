package br.com.passwordkeeper.data.source.web

import br.com.passwordkeeper.data.source.web.service.AdviceService
import br.com.passwordkeeper.domain.result.webclient.GetAdviceWebClientResult
import java.lang.Exception

class AdviceWebClient(private val adviceService: AdviceService) {

    suspend fun getAdvice(): GetAdviceWebClientResult {
        try {
            val advice = adviceService.getAdvice()
            if (advice.isSuccessful) {
                return GetAdviceWebClientResult.Success(advice.body()?.slip)
            }
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
        return GetAdviceWebClientResult.ErrorUnknown
    }

}
