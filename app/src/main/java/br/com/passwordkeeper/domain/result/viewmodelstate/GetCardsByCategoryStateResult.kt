package br.com.passwordkeeper.domain.result.viewmodelstate

import br.com.passwordkeeper.domain.model.CardView

sealed class GetCardsByCategoryStateResult {
    data class Success(val cardViewList: List<CardView>) :
        GetCardsByCategoryStateResult()
    object ErrorUnknown : GetCardsByCategoryStateResult()
}