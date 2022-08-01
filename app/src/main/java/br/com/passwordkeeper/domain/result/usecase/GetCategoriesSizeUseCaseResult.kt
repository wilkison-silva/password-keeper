package br.com.passwordkeeper.domain.result.usecase

import br.com.passwordkeeper.domain.model.CategoryView

sealed class GetCategoriesSizeUseCaseResult {
    data class Success(val categoriesViewList: List<CategoryView>) :
        GetCategoriesSizeUseCaseResult()
    object ErrorUnknown : GetCategoriesSizeUseCaseResult()
}