package br.com.passwordkeeper.domain.result.usecase

import br.com.passwordkeeper.domain.model.CardView

sealed class GetAllCardsUseCaseResult {
    data class SuccessWithCards(val cardViewList: List<CardView>) : GetAllCardsUseCaseResult()
    object ErrorUnknown: GetAllCardsUseCaseResult()
}