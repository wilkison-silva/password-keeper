package br.com.passwordkeeper.domain.usecases.get_items_count_by_categories

import br.com.passwordkeeper.presentation.model.CategoryView

sealed class GetCategoriesSizeUseCaseResult {
    data class Success(val categoriesViewList: List<CategoryView>) :
        GetCategoriesSizeUseCaseResult()
    object ErrorUnknown : GetCategoriesSizeUseCaseResult()
}