package br.com.passwordkeeper.domain.usecases.get_items_count_by_categories

import br.com.passwordkeeper.domain.result.usecase.GetCategoriesSizeUseCaseResult

interface GetItemsCountByCategoriesUseCase {

    suspend operator fun invoke(email: String): GetCategoriesSizeUseCaseResult
}