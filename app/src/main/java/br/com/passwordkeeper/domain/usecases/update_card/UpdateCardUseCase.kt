package br.com.passwordkeeper.domain.usecases.update_card

import br.com.passwordkeeper.commons.Categories
import br.com.passwordkeeper.domain.result.usecase.UpdateCardUseCaseResult

interface UpdateCardUseCase {

    suspend fun invoke(
        cardId: String,
        description: String,
        login: String,
        password: String,
        category: Categories,
        isFavorite: Boolean,
        date: String,
        emailUser: String
    ): UpdateCardUseCaseResult

}