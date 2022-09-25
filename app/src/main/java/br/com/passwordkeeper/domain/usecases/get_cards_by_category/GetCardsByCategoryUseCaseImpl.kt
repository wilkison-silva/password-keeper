package br.com.passwordkeeper.domain.usecases.get_cards_by_category

import br.com.passwordkeeper.domain.mapper.CardDomainMapper
import br.com.passwordkeeper.domain.repository.CardRepository
import br.com.passwordkeeper.domain.repository.GetCardsByCategoryRepositoryResult

class GetCardsByCategoryUseCaseImpl(
    private val cardRepository: CardRepository,
    private val cardDomainMapper: CardDomainMapper
) : GetCardsByCategoryUseCase {

    override suspend operator fun invoke(
        category: String,
        email: String,
    ): GetCardsByCategoryUseCaseResult {
        return when (val getCardsByCategoryRepositoryResult =
            cardRepository.getCardsByCategory(category, email)) {
            is GetCardsByCategoryRepositoryResult.Success -> {
                val cardDomainList = getCardsByCategoryRepositoryResult.cardDomainList
                val cardViewList = cardDomainMapper.transform(cardDomainList)
                GetCardsByCategoryUseCaseResult.Success(cardViewList)
            }
            is GetCardsByCategoryRepositoryResult.ErrorUnknown -> {
                GetCardsByCategoryUseCaseResult.ErrorUnknown
            }
        }
    }

}