package br.com.passwordkeeper.domain.model

data class CardFirestore(
    val description: String = "",
    val login: String = "",
    val password: String = "",
    val type: String = ""
) {
    fun convertToCardData() : CardData {
        return CardData(
            description = description,
            login = login,
            password = password,
            type = type
        )
    }
}