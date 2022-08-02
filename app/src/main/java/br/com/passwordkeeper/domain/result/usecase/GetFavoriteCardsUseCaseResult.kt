package br.com.passwordkeeper.domain.result.usecase

import br.com.passwordkeeper.domain.model.CardView

sealed class GetFavoriteCardsUseCaseResult {
    data class Success(val cardViewList: List<CardView>) : GetFavoriteCardsUseCaseResult()
    object ErrorUnknown: GetFavoriteCardsUseCaseResult()
}