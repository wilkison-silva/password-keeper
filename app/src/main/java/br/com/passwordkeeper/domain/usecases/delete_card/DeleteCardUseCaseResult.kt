package br.com.passwordkeeper.domain.usecases.delete_card

sealed class DeleteCardUseCaseResult {
    object Success : DeleteCardUseCaseResult()
    object ErrorUnknown: DeleteCardUseCaseResult()
}