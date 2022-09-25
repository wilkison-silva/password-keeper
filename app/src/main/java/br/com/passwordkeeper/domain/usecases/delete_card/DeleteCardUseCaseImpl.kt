package br.com.passwordkeeper.domain.usecases.delete_card

import br.com.passwordkeeper.domain.repository.CardRepository
import br.com.passwordkeeper.domain.repository.DeleteCardRepositoryResult

class DeleteCardUseCaseImpl(
    private val cardRepository: CardRepository,
) : DeleteCardUseCase {

    override suspend operator fun invoke(cardId: String): DeleteCardUseCaseResult {
        return when (cardRepository.deleteCard(cardId)) {
            is DeleteCardRepositoryResult.Success -> {
                DeleteCardUseCaseResult.Success
            }
            is DeleteCardRepositoryResult.ErrorUnknown -> {
                DeleteCardUseCaseResult.ErrorUnknown
            }
        }
    }

}