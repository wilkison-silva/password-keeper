package br.com.passwordkeeper.data.model

import br.com.passwordkeeper.data.model.AdviceData
import com.squareup.moshi.Json

data class SlipResponse(

    @field:Json(name = "slip") val slip: AdviceData
)

