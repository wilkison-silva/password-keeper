package br.com.passwordkeeper.domain.model

data class CardDomain(
    val cardId: String,
    val description: String,
    val login: String,
    val password: String,
    val category: String,
    val isFavorite: Boolean,
    val date: String
)