package br.com.passwordkeeper.data.model

import com.google.firebase.firestore.DocumentReference

data class CardFirestore(
    val id: String = "",
    val description: String = "",
    val login: String = "",
    val password: String = "",
    val category: String = "",
    val user: DocumentReference? = null,
    val favorite: Boolean = false,
    val date: String = ""
)