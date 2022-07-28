package br.com.passwordkeeper.domain.model

import com.google.firebase.firestore.DocumentReference

data class CardData(
    val cardId: String? = null,
    val description: String,
    val login: String,
    val password: String,
    val category: String
) {
    fun convertToCardFireStore(userDocumentReference: DocumentReference): CardFirestore {
        return CardFirestore(
            description = description,
            login = login,
            password = password,
            category = category,
            userDocumentReference = userDocumentReference
        )
    }

    fun convertToCardDomain(): CardDomain {
        return CardDomain(
            cardId = cardId ?: "",
            description = description,
            login = login,
            password = password,
            category = category
        )
    }
}