package br.com.passwordkeeper.domain.result.repository

sealed class DeleteCardRepositoryResult {
    object Success : DeleteCardRepositoryResult()
    object ErrorUnknown: DeleteCardRepositoryResult()
}