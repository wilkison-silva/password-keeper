package br.com.passwordkeeper.presentation.features.list_cards.states

import br.com.passwordkeeper.presentation.model.CardView

sealed class GetCardsState {
    data class Success(val cardViewList: List<CardView>) : GetCardsState()
    object Loading: GetCardsState()
    object ErrorUnknown: GetCardsState()
    object EmptyState: GetCardsState()
}