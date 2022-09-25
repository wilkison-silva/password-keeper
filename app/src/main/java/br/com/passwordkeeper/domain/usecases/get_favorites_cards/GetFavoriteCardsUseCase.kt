package br.com.passwordkeeper.domain.usecases.get_favorites_cards

interface GetFavoriteCardsUseCase {

    suspend operator fun invoke(email: String): GetFavoriteCardsUseCaseResult

}