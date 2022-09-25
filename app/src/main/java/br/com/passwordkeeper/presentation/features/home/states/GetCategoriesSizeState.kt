package br.com.passwordkeeper.presentation.features.home.states

import br.com.passwordkeeper.presentation.model.CategoryView

sealed class GetCategoriesSizeState {
    data class Success(val categoriesViewList: List<CategoryView>) :
        GetCategoriesSizeState()
    object ErrorUnknown : GetCategoriesSizeState()
    object NoElements: GetCategoriesSizeState()
    object Loading: GetCategoriesSizeState()
}