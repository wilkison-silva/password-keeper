package br.com.passwordkeeper.domain.usecases.get_favorites_cards

import br.com.passwordkeeper.domain.result.usecase.GetFavoriteCardsUseCaseResult

interface GetFavoriteCardsUseCase {

    suspend operator fun invoke(email: String): GetFavoriteCardsUseCaseResult

}