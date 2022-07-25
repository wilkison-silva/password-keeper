package br.com.passwordkeeper.domain.result.usecase

sealed class DeleteCardUseCaseResult {
    object Success : DeleteCardUseCaseResult()
    object ErrorUnknown: DeleteCardUseCaseResult()
}