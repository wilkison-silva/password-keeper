package br.com.passwordkeeper.domain.result.usecase

import br.com.passwordkeeper.domain.model.CategoryView

sealed class GetCategoriesUseCaseResult {
    data class SuccessWithElements(val categoriesViewList: List<CategoryView>) :
        GetCategoriesUseCaseResult()
    object SuccessWithoutElements: GetCategoriesUseCaseResult()
    object ErrorUnknown : GetCategoriesUseCaseResult()
}