package br.com.passwordkeeper.domain.usecases.create_card

import br.com.passwordkeeper.commons.Categories

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