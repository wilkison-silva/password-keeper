package br.com.passwordkeeper.data.source.web.service

import br.com.passwordkeeper.data.model.SlipResponse
import retrofit2.Response
import retrofit2.http.GET

interface AdviceService {

    @GET("/advice")
    suspend fun getAdvice(): Response<SlipResponse>
}