package br.com.passwordkeeper.domain.usecases.delete_card

import br.com.passwordkeeper.domain.result.usecase.DeleteCardUseCaseResult

interface DeleteCardUseCase {

    suspend operator fun invoke(cardId: String): DeleteCardUseCaseResult

}