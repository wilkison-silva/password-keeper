package br.com.passwordkeeper.domain.usecase

interface PasswordValidationUseCase {

    fun isValidPassword(password: String): Boolean

}