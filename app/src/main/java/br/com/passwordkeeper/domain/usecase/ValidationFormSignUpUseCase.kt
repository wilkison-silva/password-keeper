package br.com.passwordkeeper.domain.usecase

import br.com.passwordkeeper.domain.result.usecase.ValidationFormSignUpUseCaseResult

interface ValidationFormSignUpUseCase {

    fun validateForm(
        name: String,
        email: String,
        password: String
    ): ValidationFormSignUpUseCaseResult

}