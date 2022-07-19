package br.com.passwordkeeper.data.source.web.model

import com.squareup.moshi.Json

data class AdviceResponse(

    @field:Json(name = "id") val id: Int,
    @field:Json(name = "advice") val advice: String
)
