package br.com.passwordkeeper.domain.usecases.get_card_by_id

interface GetCardByIdUseCase {

    suspend operator fun invoke(cardId: String): GetCardByIdUseCaseResult

}