package br.com.passwordkeeper.domain.usecases.form_validation_sign_in

import br.com.passwordkeeper.extensions.isValidEmail

class FormValidationSignInUseCaseImpl : FormValidationSignInUseCase {

    override fun invoke(email: String, password: String): FormValidationSignInUseCaseResult {
        if (email.isBlank())
            return FormValidationSignInUseCaseResult.ErrorEmailIsBlank
        if (!email.isValidEmail())
            return FormValidationSignInUseCaseResult.ErrorEmailMalFormed
        if (password.isBlank())
            return FormValidationSignInUseCaseResult.ErrorPasswordIsBlank
        return FormValidationSignInUseCaseResult.Success
    }
}