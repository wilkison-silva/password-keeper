package br.com.passwordkeeper.domain.model

import com.squareup.moshi.Json

data class SlipResponse(

    @field:Json(name = "slip") val slip: AdviceResponse
)

