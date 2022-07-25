package br.com.passwordkeeper.domain.model

data class CardDomain(
    val cardId: String,
    val description: String,
    val login: String,
    val password: String,
    val type: String
) {
    fun convertToCardView() : CardView {
        return CardView(
            cardId = cardId,
            description = description,
            login = login,
            password = password,
            type = String()
        )
    }

    fun convertToCardData(): CardData {
        return CardData(
            cardId = cardId,
            description = description,
            login = login,
            password = password,
            type = type
        )
    }
}