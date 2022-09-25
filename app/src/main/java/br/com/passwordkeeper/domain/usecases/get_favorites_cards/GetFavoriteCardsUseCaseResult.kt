package br.com.passwordkeeper.domain.usecases.get_favorites_cards

import br.com.passwordkeeper.presentation.model.CardView

sealed class GetFavoriteCardsUseCaseResult {
    data class Success(val cardViewList: List<CardView>) : GetFavoriteCardsUseCaseResult()
    object ErrorUnknown: GetFavoriteCardsUseCaseResult()
}