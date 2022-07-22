package br.com.passwordkeeper.domain.model

data class AdviceView(
    val advice: String,
    val firstLetter: String,
    val quantityWords: Int
)
