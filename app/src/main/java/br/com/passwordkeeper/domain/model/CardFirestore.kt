package br.com.passwordkeeper.domain.model

import com.google.firebase.firestore.DocumentReference

data class CardFirestore(
    val description: String = "",
    val login: String = "",
    val password: String = "",
    val category: String = "",
    val user: DocumentReference? = null,
    val favorite: Boolean = false,
    val date: String = ""
) {
    fun convertToCardData(cardId: String) : CardData {
        return CardData(
            cardId = cardId,
            description = description,
            login = login,
            password = password,
            category = category,
            isFavorite = favorite,
            date = date
        )
    }
}