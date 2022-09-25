package br.com.passwordkeeper.domain.usecases.create_card

import br.com.passwordkeeper.commons.Categories
import br.com.passwordkeeper.domain.mapper.CardDomainMapper
import br.com.passwordkeeper.domain.mapper.CategoryDomainMapper
import br.com.passwordkeeper.domain.repository.CardRepository
import br.com.passwordkeeper.domain.result.repository.CreateCardRepositoryResult
import br.com.passwordkeeper.domain.result.usecase.CreateCardUseCaseResult

class CreateCardUseCaseImpl(
    private val cardRepository: CardRepository
) : CreateCardUseCase {

    override suspend operator fun invoke(
        description: String,
        login: String,
        password: String,
        category: Categories,
        isFavorite: Boolean,
        date: String,
        emailUser: String,
    ): CreateCardUseCaseResult {
        val createCardRepositoryResult = cardRepository.createCard(
            description = description,
            login = login,
            password = password,
            category = category,
            isFavorite = isFavorite,
            date = date,
            emailUser = emailUser
        )
        return when (createCardRepositoryResult) {
            is CreateCardRepositoryResult.Success -> {
                val cardId = createCardRepositoryResult.cardId
                CreateCardUseCaseResult.Success(cardId)
            }
            is CreateCardRepositoryResult.ErrorUnknown -> {
                CreateCardUseCaseResult.ErrorUnknown
            }
        }
    }
}