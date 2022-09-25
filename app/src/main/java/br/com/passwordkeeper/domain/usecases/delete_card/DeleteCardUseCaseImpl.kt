package br.com.passwordkeeper.domain.usecases.delete_card

import br.com.passwordkeeper.domain.repository.CardRepository
import br.com.passwordkeeper.domain.result.repository.DeleteCardRepositoryResult
import br.com.passwordkeeper.domain.result.usecase.DeleteCardUseCaseResult

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