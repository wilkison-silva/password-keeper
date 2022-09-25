package br.com.passwordkeeper.domain.usecases.get_cards_by_category

import br.com.passwordkeeper.presentation.model.CardView

sealed class GetCardsByCategoryUseCaseResult {
    data class Success(val cardViewList: List<CardView>) :
        GetCardsByCategoryUseCaseResult()
    object ErrorUnknown : GetCardsByCategoryUseCaseResult()
}