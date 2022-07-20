package br.com.passwordkeeper.domain.model

data class User(
    private val email: String,
    private val name: String
) {
    fun convertToUserView(): UserView {
        return UserView(
            name = this.name,
            firstCharacterName = this.name.substring(0, 1)
        )
    }
}