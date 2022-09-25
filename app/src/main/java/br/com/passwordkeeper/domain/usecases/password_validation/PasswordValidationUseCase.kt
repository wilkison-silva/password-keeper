package br.com.passwordkeeper.domain.usecases.password_validation

import br.com.passwordkeeper.domain.result.usecase.PasswordValidationUseCaseResult

interface PasswordValidationUseCase {

    operator fun invoke(password: String): PasswordValidationUseCaseResult

}