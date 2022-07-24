package br.com.passwordkeeper.domain.model

data class CardFirestore(
    val description: String = "",
    val login: String = "",
    val password: String = "",
    val type: String = ""
) {

}