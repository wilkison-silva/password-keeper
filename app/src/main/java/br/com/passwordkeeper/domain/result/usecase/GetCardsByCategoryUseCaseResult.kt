package br.com.passwordkeeper.domain.result.usecase

import br.com.passwordkeeper.domain.model.CardView

sealed class GetCardsByCategoryUseCaseResult {
    data class Success(val cardViewList: List<CardView>) :
        GetCardsByCategoryUseCaseResult()
    object ErrorUnknown : GetCardsByCategoryUseCaseResult()
}