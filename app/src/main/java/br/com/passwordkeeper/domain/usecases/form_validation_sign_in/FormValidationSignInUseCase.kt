package br.com.passwordkeeper.domain.usecases.form_validation_sign_in

import br.com.passwordkeeper.domain.result.usecase.FormValidationSignInUseCaseResult

interface FormValidationSignInUseCase {

    operator fun invoke(email: String, password: String): FormValidationSignInUseCaseResult

}