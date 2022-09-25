package br.com.passwordkeeper.domain.usecases.get_all_cards

import br.com.passwordkeeper.presentation.model.CardView

sealed class GetAllCardsUseCaseResult {
    data class Success(val cardViewList: List<CardView>) : GetAllCardsUseCaseResult()
    object ErrorUnknown: GetAllCardsUseCaseResult()
}