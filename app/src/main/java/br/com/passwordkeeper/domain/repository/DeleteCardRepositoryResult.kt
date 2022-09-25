package br.com.passwordkeeper.domain.repository

sealed class DeleteCardRepositoryResult {
    object Success : DeleteCardRepositoryResult()
    object ErrorUnknown: DeleteCardRepositoryResult()
}