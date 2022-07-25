package br.com.passwordkeeper.domain.result.usecase

sealed class UpdateCardUseCaseResult {
    data class Success(val cardId: String) : UpdateCardUseCaseResult()
    object ErrorUnknown: UpdateCardUseCaseResult()
}