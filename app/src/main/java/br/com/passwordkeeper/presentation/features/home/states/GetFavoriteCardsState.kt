package br.com.passwordkeeper.presentation.features.home.states

import br.com.passwordkeeper.presentation.model.CardView

sealed class GetFavoriteCardsState {
    data class Success(val cardViewList: List<CardView>) : GetFavoriteCardsState()
    object ErrorUnknown: GetFavoriteCardsState()
    object NoElements: GetFavoriteCardsState()
    object Loading: GetFavoriteCardsState()
}