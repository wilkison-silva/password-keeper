package br.com.passwordkeeper.domain.usecase

import br.com.passwordkeeper.domain.result.usecase.FormValidationSignInUseCaseResult
import br.com.passwordkeeper.extensions.isValidEmail

class FormValidationSignInUseCaseImpl : FormValidationSignInUseCase {

    override fun validateForm(
        email: String,
        password: String
    ): FormValidationSignInUseCaseResult {
        if (email.isBlank())
            return FormValidationSignInUseCaseResult.ErrorEmailIsBlank
        if (!email.isValidEmail())
            return FormValidationSignInUseCaseResult.ErrorEmailMalFormed
        if (password.isBlank())
            return FormValidationSignInUseCaseResult.ErrorPasswordIsBlank
        return FormValidationSignInUseCaseResult.Success
    }
}