package br.com.passwordkeeper.data.source.web

import br.com.passwordkeeper.data.source.web.service.AdviceService
import br.com.passwordkeeper.domain.result.GetAdviceWebClientResult
import java.lang.Exception

class AdviceWebClient(private val adviceService: AdviceService) {

    suspend fun getAdvice(): GetAdviceWebClientResult {
        try {
            val response = adviceService.getAdvice()
            if (response.isSuccessful) {
                return GetAdviceWebClientResult.Success(response.body()?.slip)
            }
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
        return GetAdviceWebClientResult.ErrorUnknown
    }

}
