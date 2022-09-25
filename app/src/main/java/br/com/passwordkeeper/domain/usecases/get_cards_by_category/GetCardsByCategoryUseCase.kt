package br.com.passwordkeeper.domain.usecases.get_cards_by_category

interface GetCardsByCategoryUseCase {

    suspend operator fun invoke(category: String, email: String): GetCardsByCategoryUseCaseResult
}