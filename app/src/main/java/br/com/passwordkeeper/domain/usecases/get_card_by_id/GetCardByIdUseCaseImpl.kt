package br.com.passwordkeeper.domain.usecases.get_card_by_id

import br.com.passwordkeeper.domain.mapper.CardDomainMapper
import br.com.passwordkeeper.domain.repository.CardRepository
import br.com.passwordkeeper.domain.result.repository.GetCardByIdRepositoryResult
import br.com.passwordkeeper.domain.result.usecase.GetCardByIdUseCaseResult

class GetCardByIdUseCaseImpl(
    private val cardRepository: CardRepository,
    private val cardDomainMapper: CardDomainMapper,
) : GetCardByIdUseCase{

    override suspend operator fun invoke(cardId: String): GetCardByIdUseCaseResult {
        return when (val getCardByIdRepositoryResult = cardRepository.getCardById(cardId)) {
            is GetCardByIdRepositoryResult.Success -> {
                val cardDomain = getCardByIdRepositoryResult.cardDomain
                val cardView = cardDomainMapper.transform(cardDomain)
                GetCardByIdUseCaseResult.Success(cardView)
            }
            is GetCardByIdRepositoryResult.ErrorUnknown -> {
                GetCardByIdUseCaseResult.ErrorUnknown
            }
        }
    }
}