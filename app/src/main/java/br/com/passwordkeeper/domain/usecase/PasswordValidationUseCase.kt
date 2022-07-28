package br.com.passwordkeeper.domain.usecase

import br.com.passwordkeeper.domain.result.usecase.PasswordValidationUseCaseResult

interface PasswordValidationUseCase {

    fun validatePassword(password: String): PasswordValidationUseCaseResult

}