package br.com.passwordkeeper.presentation.features.list_cards.states

import br.com.passwordkeeper.presentation.model.CardView

sealed class GetAllCardsState {
    data class Success(val cardViewList: List<CardView>) : GetAllCardsState()
    object Loading: GetAllCardsState()
    object ErrorUnknown: GetAllCardsState()
}