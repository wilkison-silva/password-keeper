package br.com.passwordkeeper.data.source.web.model

import com.squareup.moshi.Json

data class SlipResponse(

    @field:Json(name = "slip") val slip: AdviceResponse
)

