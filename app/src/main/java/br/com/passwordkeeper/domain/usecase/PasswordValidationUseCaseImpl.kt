package br.com.passwordkeeper.domain.usecase

import br.com.passwordkeeper.domain.result.usecase.ErrorsValidationPassword
import br.com.passwordkeeper.domain.result.usecase.PasswordValidationUseCaseResult

private const val REGEX_UPPER_CASE = ".*[A-Z]"
private const val REGEX_LOWER_CASE = ".*[a-z]"
private const val REGEX_SPECIAL_LETTER = ".*[!@#$%^&+=-]"
private const val REGEX_NUMBER = ".*[0-9]"
private const val MIN_LENGTH_PASSWORD = 16

class PasswordValidationUseCaseImpl : PasswordValidationUseCase {

    override fun validatePassword(password: String): PasswordValidationUseCaseResult {
        val errorList = mutableListOf<ErrorsValidationPassword>()

        if (!password.contains(REGEX_UPPER_CASE.toRegex()))
            errorList.add(ErrorsValidationPassword.ErrorOneUpperLetterNotFound)

        if (!password.contains(REGEX_LOWER_CASE.toRegex()))
            errorList.add(ErrorsValidationPassword.ErrorOneLowerLetterNotFound)

        if (!password.contains(REGEX_SPECIAL_LETTER.toRegex()))
            errorList.add(ErrorsValidationPassword.ErrorOneSpecialCharacterNotFound)

        if (!password.contains(REGEX_NUMBER.toRegex()))
            errorList.add(ErrorsValidationPassword.ErrorOneNumericCharacterNotFound)

        if (password.length < MIN_LENGTH_PASSWORD)
            errorList.add(ErrorsValidationPassword.ErrorPasswordLengthNotMatch)

        if (errorList.size > 0)
            return PasswordValidationUseCaseResult.ErrorsFound(errorList)

        return PasswordValidationUseCaseResult.Success
    }
}