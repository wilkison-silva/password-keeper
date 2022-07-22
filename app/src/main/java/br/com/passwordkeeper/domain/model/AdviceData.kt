package br.com.passwordkeeper.domain.model

import com.squareup.moshi.Json

data class AdviceData(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "advice") val advice: String
) {

    fun convertToAdviceDomain(): AdviceDomain {
        return AdviceDomain(
            message = this.advice
        )
    }
}
