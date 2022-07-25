package br.com.passwordkeeper.domain.usecase

import br.com.passwordkeeper.data.repository.CardRepository
import br.com.passwordkeeper.domain.model.CardDomain
import br.com.passwordkeeper.domain.result.repository.GetAllCardsRepositoryResult
import br.com.passwordkeeper.domain.result.usecase.GetAllCardsUseCaseResult

class FirebaseCardUseCaseImpl(
    private val cardRepository: CardRepository
) : CardUseCase {
    override suspend fun getAllCards(email: String): GetAllCardsUseCaseResult {
        return when (val getAllCardsRepositoryResult = cardRepository.getAllCards(email)) {
            is GetAllCardsRepositoryResult.Success -> {
                val cardDomainList = getAllCardsRepositoryResult.cardDataList
                val cardViewList = cardDomainList.map { cardDomain: CardDomain ->
                    cardDomain.convertToCardView()
                }
                GetAllCardsUseCaseResult.Success(cardViewList)
            }
            is GetAllCardsRepositoryResult.ErrorUnknown -> {
                GetAllCardsUseCaseResult.ErrorUnknown
            }
        }
    }
}