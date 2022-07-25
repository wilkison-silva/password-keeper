package br.com.passwordkeeper.domain.result.repository

sealed class CreateCardRepositoryResult {
    data class Success(val cardId: String) : CreateCardRepositoryResult()
    object ErrorUnknown: CreateCardRepositoryResult()
}