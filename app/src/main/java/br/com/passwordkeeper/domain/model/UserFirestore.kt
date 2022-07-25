package br.com.passwordkeeper.domain.model

data class UserFirestore(
    val email: String = "",
    val name: String = "",
) {
    fun convertToUserData() : UserData {
        return UserData(
            email = email,
            name = name
        )
    }
}