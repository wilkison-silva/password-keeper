package br.com.passwordkeeper.domain.usecases.form_validation_sign_up

import br.com.passwordkeeper.domain.usecases.password_validation.PasswordValidationUseCaseResult
import br.com.passwordkeeper.domain.usecases.password_validation.PasswordValidationUseCase
import br.com.passwordkeeper.extensions.isValidEmail

class FormValidationSignUpUseCaseImpl(
    private val passwordValidationUseCase: PasswordValidationUseCase,
) : FormValidationSignUpUseCase {

    override fun invoke(
        name: String,
        email: String,
        password: String,
        confirmedPassword: String,
    ): FormValidationSignUpUseCaseResult {
        if (name.isBlank())
            return FormValidationSignUpUseCaseResult.ErrorNameIsBlank
        if (email.isBlank())
            return FormValidationSignUpUseCaseResult.ErrorEmailIsBlank
        if (!email.isValidEmail())
            return FormValidationSignUpUseCaseResult.ErrorEmailMalFormed
        if (password.isBlank())
            return FormValidationSignUpUseCaseResult.ErrorPasswordIsBlank
        when (val passwordValidationResult = passwordValidationUseCase(password)) {
            is PasswordValidationUseCaseResult.ErrorsFound -> {
                if (passwordValidationResult.errorList.isNotEmpty()) {
                    return FormValidationSignUpUseCaseResult.ErrorPasswordTooWeak
                }
            }
            is PasswordValidationUseCaseResult.PasswordFieldEmpty -> {
                return FormValidationSignUpUseCaseResult.ErrorPasswordTooWeak
            }
        }
        if (password != confirmedPassword)
            return FormValidationSignUpUseCaseResult.ErrorPasswordsDoNotMatch

        return FormValidationSignUpUseCaseResult.Success
    }

}