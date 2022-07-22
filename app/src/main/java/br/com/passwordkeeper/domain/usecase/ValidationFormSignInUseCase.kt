package br.com.passwordkeeper.domain.usecase

import br.com.passwordkeeper.domain.result.usecase.ValidationFormSignInUseCaseResult

interface ValidationFormSignInUseCase {

    fun validateForm(email: String, password: String): ValidationFormSignInUseCaseResult

}