package br.com.passwordkeeper.domain.usecase

import br.com.passwordkeeper.domain.result.usecase.ValidationFormSignInUseCaseResult
import br.com.passwordkeeper.extensions.isValidEmail

class ValidationFormSignInUseCaseImpl : ValidationFormSignInUseCase {

    override fun validateForm(
        email: String,
        password: String
    ): ValidationFormSignInUseCaseResult {
        if (email.isBlank())
            return ValidationFormSignInUseCaseResult.ErrorEmailIsBlank
        if (!email.isValidEmail())
            return ValidationFormSignInUseCaseResult.ErrorEmailMalFormed
        if (password.isBlank())
            return ValidationFormSignInUseCaseResult.ErrorPasswordIsBlank
        return ValidationFormSignInUseCaseResult.Success
    }
}