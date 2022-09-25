package br.com.passwordkeeper.domain.result.viewmodelstate

import br.com.passwordkeeper.presentation.model.CardView

sealed class GetFavoriteCardsStateResult {
    data class Success(val cardViewList: List<CardView>) : GetFavoriteCardsStateResult()
    object ErrorUnknown: GetFavoriteCardsStateResult()
    object NoElements: GetFavoriteCardsStateResult()
    object Loading: GetFavoriteCardsStateResult()
}