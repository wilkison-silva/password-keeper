package br.com.passwordkeeper.domain.usecases.get_cards_by_category

import br.com.passwordkeeper.domain.result.usecase.GetCardsByCategoryUseCaseResult

interface GetCardsByCategoryUseCase {

    suspend operator fun invoke(category: String, email: String): GetCardsByCategoryUseCaseResult
}