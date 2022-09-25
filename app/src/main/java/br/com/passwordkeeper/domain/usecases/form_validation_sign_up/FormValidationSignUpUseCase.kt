package br.com.passwordkeeper.domain.usecases.form_validation_sign_up

interface FormValidationSignUpUseCase {

    operator fun invoke(
        name: String,
        email: String,
        password: String,
        confirmedPassword: String
    ): FormValidationSignUpUseCaseResult

}