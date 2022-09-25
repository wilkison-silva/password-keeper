package br.com.passwordkeeper.domain.usecases.form_validation_sign_up

import br.com.passwordkeeper.domain.result.usecase.FormValidationSignUpUseCaseResult

interface FormValidationSignUpUseCase {

    operator fun invoke(
        name: String,
        email: String,
        password: String,
        confirmedPassword: String
    ): FormValidationSignUpUseCaseResult

}