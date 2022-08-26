package br.com.passwordkeeper.domain.model

data class CardDomain(
    val cardId: String? = null,
    val description: String,
    val login: String,
    val password: String,
    val category: String,
    val isFavorite: Boolean,
    val date: String
) {
    fun convertToCardView() : CardView {
        return CardView(
            cardId = cardId ?: "",
            description = description,
            login = login,
            password = password,
            category = category,
            favorite = isFavorite,
            date = date
        )
    }

    fun convertToCardData(): CardData {
        return CardData(
            cardId = cardId,
            description = description,
            login = login,
            password = password,
            category = category,
            isFavorite = isFavorite,
            date = date
        )
    }
}