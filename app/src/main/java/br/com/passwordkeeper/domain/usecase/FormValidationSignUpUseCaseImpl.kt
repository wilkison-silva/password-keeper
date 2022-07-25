package br.com.passwordkeeper.domain.usecase

import br.com.passwordkeeper.domain.result.usecase.FormValidationSignUpUseCaseResult
import br.com.passwordkeeper.extensions.isValidEmail

class FormValidationSignUpUseCaseImpl(
    private val passwordValidationUseCase: PasswordValidationUseCase
) : FormValidationSignUpUseCase {

    override fun validateForm(
        name: String,
        email: String,
        password: String,
        confirmedPassword: String
    ): FormValidationSignUpUseCaseResult {
        if (name.isBlank())
            return FormValidationSignUpUseCaseResult.ErrorNameIsBlank
        if (email.isBlank())
            return FormValidationSignUpUseCaseResult.ErrorEmailIsBlank
        if (!email.isValidEmail())
            return FormValidationSignUpUseCaseResult.ErrorEmailMalFormed
        if (password.isBlank())
            return FormValidationSignUpUseCaseResult.ErrorPasswordIsBlank
        if (!passwordValidationUseCase.isValidPassword(password))
            return FormValidationSignUpUseCaseResult.ErrorPasswordTooWeak
        if (password != confirmedPassword)
            return FormValidationSignUpUseCaseResult.ErrorPasswordsDoNotMatch


        return FormValidationSignUpUseCaseResult.Success
    }

}