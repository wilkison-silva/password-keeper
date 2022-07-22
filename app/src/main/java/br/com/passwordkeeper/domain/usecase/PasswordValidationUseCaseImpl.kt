package br.com.passwordkeeper.domain.usecase

import java.util.regex.Pattern

class PasswordValidationUseCaseImpl : PasswordValidationUseCase {

    override fun isValidPassword(password: String): Boolean {
        val passwordREGEX = Pattern.compile(
            "^" +
                    "(?=.*[0-9])" +
                    "(?=.*[a-z])" +
                    "(?=.*[A-Z])" +
                    "(?=.*[a-zA-Z])" +
                    "(?=.*[@#$%^&+=])" +
                    "(?=\\S+$)" +
                    ".{16,}" +
                    "$"
        )
        return passwordREGEX.matcher(password).matches()
    }
}