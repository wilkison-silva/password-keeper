package br.com.passwordkeeper.domain.result.usecase

import br.com.passwordkeeper.presentation.model.CardView

sealed class GetCardByIdUseCaseResult {
    data class Success(val cardView: CardView) : GetCardByIdUseCaseResult()
    object ErrorUnknown: GetCardByIdUseCaseResult()
}