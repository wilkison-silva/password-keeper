package br.com.passwordkeeper.domain.model

import com.google.firebase.firestore.DocumentReference

data class CardData(
    var cardId: String? = null,
    val description: String,
    val login: String,
    val password: String,
    val category: String,
    val isFavorite: Boolean,
    val date: String
) {
    fun convertToCardFireStore(userDocumentReference: DocumentReference): CardFirestore {
        return CardFirestore(
            description = description,
            login = login,
            password = password,
            category = category,
            user = userDocumentReference,
            favorite = isFavorite,
            date = date
        )
    }

}