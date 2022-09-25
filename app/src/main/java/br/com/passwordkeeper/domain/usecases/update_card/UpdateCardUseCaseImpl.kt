package br.com.passwordkeeper.domain.usecases.update_card

import br.com.passwordkeeper.commons.Categories
import br.com.passwordkeeper.domain.repository.CardRepository
import br.com.passwordkeeper.domain.result.repository.UpdateCardRepositoryResult
import br.com.passwordkeeper.domain.result.usecase.UpdateCardUseCaseResult

class UpdateCardUseCaseImpl(
    private val cardRepository: CardRepository
) : UpdateCardUseCase {

    override suspend operator fun invoke(
        cardId: String,
        description: String,
        login: String,
        password: String,
        category: Categories,
        isFavorite: Boolean,
        date: String,
        emailUser: String
    ): UpdateCardUseCaseResult {
        val updateCardUseCaseResult =
            cardRepository.updateCard(
                cardId = cardId,
                description = description,
                login = login,
                password = password,
                category = category,
                isFavorite = isFavorite,
                date = date,
                emailUser = emailUser
            )
        return when (updateCardUseCaseResult) {
            is UpdateCardRepositoryResult.Success -> {
                val cardId = updateCardUseCaseResult.cardId
                UpdateCardUseCaseResult.Success(cardId)
            }
            is UpdateCardRepositoryResult.ErrorUnknown -> {
                UpdateCardUseCaseResult.ErrorUnknown
            }
        }
    }

}