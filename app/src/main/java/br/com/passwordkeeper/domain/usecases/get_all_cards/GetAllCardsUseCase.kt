package br.com.passwordkeeper.domain.usecases.get_all_cards

interface GetAllCardsUseCase {

    suspend operator fun invoke(email: String): GetAllCardsUseCaseResult

}