package br.com.passwordkeeper.domain.usecases.get_all_cards

import br.com.passwordkeeper.domain.result.usecase.GetAllCardsUseCaseResult

interface GetAllCardsUseCase {

    suspend operator fun invoke(email: String): GetAllCardsUseCaseResult

}