package br.com.passwordkeeper.domain.usecases.get_card_by_id

import br.com.passwordkeeper.presentation.model.CardView

sealed class GetCardByIdUseCaseResult {
    data class Success(val cardView: CardView) : GetCardByIdUseCaseResult()
    object ErrorUnknown: GetCardByIdUseCaseResult()
}