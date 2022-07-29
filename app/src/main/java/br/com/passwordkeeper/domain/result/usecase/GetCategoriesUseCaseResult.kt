package br.com.passwordkeeper.domain.result.usecase

import br.com.passwordkeeper.domain.model.CategoryView

sealed class GetCategoriesUseCaseResult {
    data class Success(val categoryViewList: List<CategoryView>) :
        GetCategoriesUseCaseResult()
    object ErrorUnknown : GetCategoriesUseCaseResult()
}