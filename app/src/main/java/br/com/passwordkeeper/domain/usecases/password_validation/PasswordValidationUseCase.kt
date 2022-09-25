package br.com.passwordkeeper.domain.usecases.password_validation

interface PasswordValidationUseCase {

    operator fun invoke(password: String): PasswordValidationUseCaseResult

}