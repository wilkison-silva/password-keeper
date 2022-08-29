package br.com.passwordkeeper.domain.usecase

import br.com.passwordkeeper.domain.result.usecase.FormValidationCardUseCaseResult

interface FormValidationCardUseCase {

    fun validateForm(
        description: String,
        login: String,
        password: String,
        category: String,
        isFavorite: Boolean,
        date: String
    ): FormValidationCardUseCaseResult

}