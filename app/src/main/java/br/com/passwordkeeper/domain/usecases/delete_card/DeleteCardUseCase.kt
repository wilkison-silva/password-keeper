package br.com.passwordkeeper.domain.usecases.delete_card

interface DeleteCardUseCase {

    suspend operator fun invoke(cardId: String): DeleteCardUseCaseResult

}