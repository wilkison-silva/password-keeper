package br.com.passwordkeeper.domain.usecases.form_validation_create_card

import br.com.passwordkeeper.domain.result.usecase.FormValidationCardUseCaseResult

interface FormValidationCreateCardUseCase {

    operator fun invoke(
        description: String,
        login: String,
        password: String,
        category: String,
        isFavorite: Boolean,
        date: String
    ): FormValidationCardUseCaseResult

}