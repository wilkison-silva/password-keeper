package br.com.passwordkeeper.domain.usecases.get_items_count_by_categories

interface GetItemsCountByCategoriesUseCase {

    suspend operator fun invoke(email: String): GetCategoriesSizeUseCaseResult
}