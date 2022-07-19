package br.com.passwordkeeper.domain.model

import br.com.passwordkeeper.domain.model.Advice
import com.squareup.moshi.Json

data class AdviceResponse(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "advice") val advice: String
) {

    fun convertToAdvice(): Advice {
        return Advice(
            message = this.advice
        )
    }
}
