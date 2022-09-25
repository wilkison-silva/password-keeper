package br.com.passwordkeeper.domain.result.viewmodelstate

import br.com.passwordkeeper.presentation.model.CardView

sealed class GetAllCardsStateResult {
    data class Success(val cardViewList: List<CardView>) : GetAllCardsStateResult()
    object Loading: GetAllCardsStateResult()
    object ErrorUnknown: GetAllCardsStateResult()
}