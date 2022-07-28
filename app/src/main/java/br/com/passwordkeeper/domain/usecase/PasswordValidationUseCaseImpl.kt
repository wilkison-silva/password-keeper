package br.com.passwordkeeper.domain.usecase

import br.com.passwordkeeper.domain.result.usecase.PasswordValidationUseCaseResult

private const val REGEX_UPPER_CASE = ".*[A-Z]"
private const val REGEX_LOWER_CASE = ".*[a-z]"
private const val REGEX_SPECIAL_LETTER = ".*[!@#$%^&+=-]"
private const val REGEX_NUMBER = ".*[0-9]"
private const val MIN_LENGTH_PASSWORD = 16

class PasswordValidationUseCaseImpl : PasswordValidationUseCase {

    override fun validatePassword(password: String): PasswordValidationUseCaseResult {
        if (!password.contains(REGEX_UPPER_CASE.toRegex()))
            return PasswordValidationUseCaseResult.ErrorOneUpperLetterNotFound

        if (!password.contains(REGEX_LOWER_CASE.toRegex()))
            return PasswordValidationUseCaseResult.ErrorOneLowerLetterNotFound

        if (!password.contains(REGEX_SPECIAL_LETTER.toRegex()))
            return PasswordValidationUseCaseResult.ErrorOneSpecialCharacterNotFound

        if (!password.contains(REGEX_NUMBER.toRegex()))
            return PasswordValidationUseCaseResult.ErrorOneNumericCharacterNotFound

        if (password.length < MIN_LENGTH_PASSWORD)
            return PasswordValidationUseCaseResult.ErrorPasswordLengthNotMatch

        return PasswordValidationUseCaseResult.Success
    }
}