package br.com.passwordkeeper.domain.repository

sealed class CreateCardRepositoryResult {
    data class Success(val cardId: String) : CreateCardRepositoryResult()
    object ErrorUnknown: CreateCardRepositoryResult()
}