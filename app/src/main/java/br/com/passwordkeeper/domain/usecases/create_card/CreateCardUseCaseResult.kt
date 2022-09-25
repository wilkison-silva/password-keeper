package br.com.passwordkeeper.domain.usecases.create_card

sealed class CreateCardUseCaseResult {
    data class Success(val cardId: String) : CreateCardUseCaseResult()
    object ErrorUnknown: CreateCardUseCaseResult()
}