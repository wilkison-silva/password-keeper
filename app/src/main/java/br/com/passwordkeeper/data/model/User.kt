package br.com.passwordkeeper.data.model

data class User(
    private val name: String = "",
    private val email: String,
    private val password: String
)