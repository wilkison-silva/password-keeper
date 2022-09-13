package br.com.passwordkeeper.domain.result.viewmodelstate

import br.com.passwordkeeper.domain.model.CardView

sealed class GetAllCardsStateResult {
    data class Success(val cardViewList: List<CardView>) : GetAllCardsStateResult()
    object ErrorUnknown: GetAllCardsStateResult()
}