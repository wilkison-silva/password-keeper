package br.com.passwordkeeper.domain.model

data class UserData(
    val email: String = "",
    val name: String = "",
) {
    fun convertToUserDomain(): UserDomain {
        return UserDomain(
            email = this.email,
            name = this.name
        )
    }
}