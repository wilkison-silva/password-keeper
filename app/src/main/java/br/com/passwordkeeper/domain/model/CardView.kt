package br.com.passwordkeeper.domain.model

data class CardView(
    val cardId: String,
    val description: String,
    val login: String,
    val password: String,
    val category: String,
    val favorite: Boolean,
    val date: String
)