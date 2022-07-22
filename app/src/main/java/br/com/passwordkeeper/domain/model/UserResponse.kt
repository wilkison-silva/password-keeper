package br.com.passwordkeeper.domain.model

data class UserResponse(
    val email: String = "",
    val name: String = "",
) {
    fun convertToUser(): User {
        return User(
            email = this.email,
            name = this.name
        )
    }
}