package br.com.passwordkeeper.domain.repository

sealed class UpdateCardRepositoryResult {
    data class Success(val cardId: String) : UpdateCardRepositoryResult()
    object ErrorUnknown: UpdateCardRepositoryResult()
}