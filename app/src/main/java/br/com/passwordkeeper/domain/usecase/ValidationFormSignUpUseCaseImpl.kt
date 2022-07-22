package br.com.passwordkeeper.domain.usecase

import br.com.passwordkeeper.domain.result.usecase.ValidationFormSignUpUseCaseResult
import br.com.passwordkeeper.extensions.isValidEmail

class ValidationFormSignUpUseCaseImpl(
    private val passwordValidationUseCase: PasswordValidationUseCase
) : ValidationFormSignUpUseCase {

    override fun validateForm(
        name: String,
        email: String,
        password: String
    ): ValidationFormSignUpUseCaseResult {
        if (name.isBlank())
            return ValidationFormSignUpUseCaseResult.ErrorNameIsBlank
        if (email.isBlank())
            return ValidationFormSignUpUseCaseResult.ErrorEmailIsBlank
        if (!email.isValidEmail())
            return ValidationFormSignUpUseCaseResult.ErrorEmailMalFormed
        if (password.isBlank())
            return ValidationFormSignUpUseCaseResult.ErrorPasswordIsBlank
        if (!passwordValidationUseCase.isValidPassword(password))
            return ValidationFormSignUpUseCaseResult.ErrorPasswordTooWeak
        return ValidationFormSignUpUseCaseResult.Success
    }

}