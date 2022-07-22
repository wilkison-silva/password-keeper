package br.com.passwordkeeper.domain.usecase

import br.com.passwordkeeper.domain.result.usecase.FormValidationSignUpUseCaseResult

interface FormValidationSignUpUseCase {

    fun validateForm(
        name: String,
        email: String,
        password: String
    ): FormValidationSignUpUseCaseResult

}