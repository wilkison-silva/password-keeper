package br.com.passwordkeeper.domain.result.repository

sealed class UpdateCardRepositoryResult {
    data class Success(val cardId: String) : UpdateCardRepositoryResult()
    object ErrorUnknown: UpdateCardRepositoryResult()
}