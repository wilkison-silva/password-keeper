package br.com.passwordkeeper.domain.model

data class UserResponse(
    private val email: String,
    private val name: String,
) {
    fun convertToUser(): User {
        return User(
            email = this.email,
            name = this.name
        )
    }
}