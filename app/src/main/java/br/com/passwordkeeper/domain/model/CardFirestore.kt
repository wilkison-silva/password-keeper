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
)