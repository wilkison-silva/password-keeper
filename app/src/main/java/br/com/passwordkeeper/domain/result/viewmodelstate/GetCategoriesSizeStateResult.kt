package br.com.passwordkeeper.domain.result.viewmodelstate

import br.com.passwordkeeper.presentation.model.CategoryView

sealed class GetCategoriesSizeStateResult {
    data class Success(val categoriesViewList: List<CategoryView>) :
        GetCategoriesSizeStateResult()
    object ErrorUnknown : GetCategoriesSizeStateResult()
    object NoElements: GetCategoriesSizeStateResult()
    object Loading: GetCategoriesSizeStateResult()
}