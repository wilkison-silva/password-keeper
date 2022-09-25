package br.com.passwordkeeper.domain.usecases.get_card_by_id

import br.com.passwordkeeper.domain.result.usecase.GetCardByIdUseCaseResult

interface GetCardByIdUseCase {

    suspend operator fun invoke(cardId: String): GetCardByIdUseCaseResult

}