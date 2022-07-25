package br.com.passwordkeeper.domain.model

import com.google.firebase.firestore.DocumentReference

data class CardFirestore(
    val description: String = "",
    val login: String = "",
    val password: String = "",
    val type: String = "",
    val userDocumentReference: DocumentReference
) {
    fun convertToCardData(cardId: String) : CardData {
        return CardData(
            cardId = cardId,
            description = description,
            login = login,
            password = password,
            type = type
        )
    }
}