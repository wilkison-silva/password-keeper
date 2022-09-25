package br.com.passwordkeeper.domain.usecases.update_card

sealed class UpdateCardUseCaseResult {
    data class Success(val cardId: String) : UpdateCardUseCaseResult()
    object ErrorUnknown: UpdateCardUseCaseResult()
}