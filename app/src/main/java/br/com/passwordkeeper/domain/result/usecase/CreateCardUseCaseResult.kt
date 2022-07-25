package br.com.passwordkeeper.domain.result.usecase

sealed class CreateCardUseCaseResult {
    data class Success(val cardId: String) : CreateCardUseCaseResult()
    object ErrorUnknown: CreateCardUseCaseResult()
}