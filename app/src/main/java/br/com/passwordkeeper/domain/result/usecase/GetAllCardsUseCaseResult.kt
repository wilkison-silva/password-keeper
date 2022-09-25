package br.com.passwordkeeper.domain.result.usecase

import br.com.passwordkeeper.presentation.model.CardView

sealed class GetAllCardsUseCaseResult {
    data class Success(val cardViewList: List<CardView>) : GetAllCardsUseCaseResult()
    object ErrorUnknown: GetAllCardsUseCaseResult()
}