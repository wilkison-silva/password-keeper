package br.com.passwordkeeper.domain.model

import com.google.firebase.firestore.DocumentReference

data class CardData(
    val cardId: String? = null,
    val description: String,
    val login: String,
    val password: String,
    val category: String,
    val favorite: Boolean
) {
    fun convertToCardFireStore(userDocumentReference: DocumentReference): CardFirestore {
        return CardFirestore(
            description = description,
            login = login,
            password = password,
            category = category,
            user = userDocumentReference,
            favorite = favorite
        )
    }

    fun convertToCardDomain(): CardDomain {
        return CardDomain(
            cardId = cardId ?: "",
            description = description,
            login = login,
            password = password,
            category = category,
            favorite = favorite
        )
    }
}