package br.com.passwordkeeper.domain.usecase

import br.com.passwordkeeper.domain.result.usecase.PasswordValidationUseCaseResult
import java.util.regex.Pattern

class PasswordValidationUseCaseImpl : PasswordValidationUseCase {

    override fun validatePassword(password: String): PasswordValidationUseCaseResult {
//        val passwordREGEX = Pattern.compile(
//            "^" +
//                    "(?=.*[0-9])" +
//                    "(?=.*[a-z])" +
//                    "(?=.*[A-Z])" +
//                    "(?=.*[a-zA-Z])" +
//                    "(?=.*[!@#$%^&+=-])" +
//                    "(?=\\S+$)" +
//                    ".{16,}" +
//                    "$"
//        )
        val regex1Number = Pattern.compile("^" +
                "(?=.*[0-9])" +
                "$"
        )
        if (regex1Number.matcher(password).matches())
            return PasswordValidationUseCaseResult.ErrorOneNumericCharacterNotFound
        val regex1LowerCase = Pattern.compile("^" +
                "(?=.*[a-z])" +
                "$"
        )
        if (regex1LowerCase.matcher(password).matches())
            return PasswordValidationUseCaseResult.ErrorOneLowerLetterNotFound
        val regex1UpperCase = Pattern.compile("^" +
                "(?=.*[A-Z])" +
                "$"
        )
        if (regex1UpperCase.matcher(password).matches())
            return PasswordValidationUseCaseResult.ErrorOneUpperLetterNotFound
        val regex1SpecialCharacter = Pattern.compile("^" +
                "(?=.*[!@#$%^&+=-])" +
                "$"
        )
        if (regex1SpecialCharacter.matcher(password).matches())
            return PasswordValidationUseCaseResult.ErrorOneSpecialCharacterNotFound
        if (password.length < 16)
            return PasswordValidationUseCaseResult.ErrorPasswordLengthNotMatch

        return PasswordValidationUseCaseResult.Success
    }
}