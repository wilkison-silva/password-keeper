package br.com.passwordkeeper.domain.usecases.get_favorites_cards

import br.com.passwordkeeper.domain.mapper.CardDomainMapper
import br.com.passwordkeeper.domain.repository.CardRepository
import br.com.passwordkeeper.domain.result.repository.GetFavoriteCardsRepositoryResult
import br.com.passwordkeeper.domain.result.usecase.GetFavoriteCardsUseCaseResult

class GetFavoriteCardsUseCaseImpl(
    private val cardRepository: CardRepository,
    private val cardDomainMapper: CardDomainMapper,
) : GetFavoriteCardsUseCase {

    override suspend operator fun invoke(email: String): GetFavoriteCardsUseCaseResult {
        return when (val getFavoriteCardsRepositoryResult = cardRepository.getFavorites(email)) {
            is GetFavoriteCardsRepositoryResult.Success -> {
                val cardDomainList = getFavoriteCardsRepositoryResult.cardDomainList
                val cardViewList = cardDomainMapper.transform(cardDomainList)
                GetFavoriteCardsUseCaseResult.Success(cardViewList)
            }
            is GetFavoriteCardsRepositoryResult.ErrorUnknown -> {
                GetFavoriteCardsUseCaseResult.ErrorUnknown
            }
        }
    }

}