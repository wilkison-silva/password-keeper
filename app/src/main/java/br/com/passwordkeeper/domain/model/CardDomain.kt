package br.com.passwordkeeper.domain.model

data class CardDomain(
    val cardId: String,
    val description: String,
    val login: String,
    val password: String,
    val category: String,
    val favorite: Boolean
) {
    fun convertToCardView() : CardView {
        return CardView(
            cardId = cardId,
            description = description,
            login = login,
            password = password,
            category = category,
            favorite = favorite
        )
    }

    fun convertToCardData(): CardData {
        return CardData(
            cardId = cardId,
            description = description,
            login = login,
            password = password,
            category = category,
            favorite = favorite
        )
    }
}