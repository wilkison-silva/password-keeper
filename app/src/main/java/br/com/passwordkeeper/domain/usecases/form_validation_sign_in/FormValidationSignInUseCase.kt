package br.com.passwordkeeper.domain.usecases.form_validation_sign_in

interface FormValidationSignInUseCase {

    operator fun invoke(email: String, password: String): FormValidationSignInUseCaseResult

}