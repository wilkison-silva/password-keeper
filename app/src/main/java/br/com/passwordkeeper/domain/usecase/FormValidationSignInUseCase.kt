package br.com.passwordkeeper.domain.usecase

import br.com.passwordkeeper.domain.result.usecase.FormValidationSignInUseCaseResult

interface FormValidationSignInUseCase {

    fun validateForm(email: String, password: String): FormValidationSignInUseCaseResult

}