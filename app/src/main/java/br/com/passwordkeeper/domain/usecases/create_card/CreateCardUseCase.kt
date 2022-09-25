package br.com.passwordkeeper.domain.usecases.create_card

import br.com.passwordkeeper.commons.Categories
import br.com.passwordkeeper.domain.result.usecase.CreateCardUseCaseResult

interface CreateCardUseCase {

    suspend operator fun invoke(
        description: String,
        login: String,
        password: String,
        category: Categories,
        isFavorite: Boolean,
        date: String,
        emailUser: String
    ): CreateCardUseCaseResult

}