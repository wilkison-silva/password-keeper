package br.com.passwordkeeper.data.model

import com.squareup.moshi.Json

data class AdviceData(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "advice") val advice: String
)
