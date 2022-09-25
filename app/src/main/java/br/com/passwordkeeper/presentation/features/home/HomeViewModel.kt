package br.com.passwordkeeper.presentation.features.home

import android.text.SpannableStringBuilder
import androidx.core.text.bold
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.passwordkeeper.domain.usecases.get_advice.GetAdviceUseCase
import br.com.passwordkeeper.domain.usecases.get_advice.GetAdviceUseCaseResult
import br.com.passwordkeeper.domain.usecases.get_favorites_cards.GetFavoriteCardsUseCase
import br.com.passwordkeeper.domain.usecases.get_favorites_cards.GetFavoriteCardsUseCaseResult
import br.com.passwordkeeper.domain.usecases.get_items_count_by_categories.GetCategoriesSizeUseCaseResult
import br.com.passwordkeeper.domain.usecases.get_items_count_by_categories.GetItemsCountByCategoriesUseCase
import br.com.passwordkeeper.domain.usecases.sort_cardview_list.SortCardViewListUseCase
import br.com.passwordkeeper.presentation.features.home.states.GetAdviceState
import br.com.passwordkeeper.presentation.features.home.states.GetCategoriesSizeState
import br.com.passwordkeeper.presentation.features.home.states.GetFavoriteCardsState
import br.com.passwordkeeper.presentation.model.AdviceView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getAdviceUseCase: GetAdviceUseCase,
    private val getFavoriteCardsUseCase: GetFavoriteCardsUseCase,
    private val getItemsCountByCategoriesUseCase: GetItemsCountByCategoriesUseCase,
    private val sortCardViewListUseCase: SortCardViewListUseCase
) : ViewModel() {

    private val _adviceState = MutableStateFlow<GetAdviceState>(GetAdviceState.Loading)
    val adviceState = _adviceState.asStateFlow()

    init {
        updateAdvice()
    }

    private fun updateAdvice() {
        viewModelScope.launch {
            when (val getAdviceUseCaseResult = getAdviceUseCase()) {
                is GetAdviceUseCaseResult.Success -> {
                    _adviceState.value = GetAdviceState.Success(getAdviceUseCaseResult.adviceView)
                }
                is GetAdviceUseCaseResult.SuccessAdviceWithoutMessage -> {
                    _adviceState.value = GetAdviceState.SuccessWithoutMessage
                }
                is GetAdviceUseCaseResult.ErrorUnknown -> {
                    _adviceState.value = GetAdviceState.ErrorUnknown
                }
            }
        }
    }

    fun getAdviceWithFirstLetterBold(adviceView: AdviceView): SpannableStringBuilder {
        return SpannableStringBuilder()
            .bold { append(adviceView.firstLetter) }
            .append(adviceView.advice.substring(1))
    }

    private val _favoriteCardsState =
        MutableStateFlow<GetFavoriteCardsState>(GetFavoriteCardsState.Loading)
    val favoriteCardsState = _favoriteCardsState.asStateFlow()

    fun updateFavoriteCards(email: String) {
        viewModelScope.launch {
            _favoriteCardsState.value = GetFavoriteCardsState.Loading
            when (val getFavoriteCardsUseCaseResult = getFavoriteCardsUseCase(email)) {
                is GetFavoriteCardsUseCaseResult.ErrorUnknown -> {
                    _favoriteCardsState.value = GetFavoriteCardsState.ErrorUnknown
                }
                is GetFavoriteCardsUseCaseResult.Success -> {
                    val cardViewList = getFavoriteCardsUseCaseResult.cardViewList
                    if (cardViewList.isNotEmpty()) {
                        val sortedList = sortCardViewListUseCase.sortByDate(cardViewList)
                        _favoriteCardsState.value = GetFavoriteCardsState.Success(sortedList)
                    } else
                        _favoriteCardsState.value = GetFavoriteCardsState.NoElements
                }
            }
        }
    }

    private val _categoriesSizeState =
        MutableStateFlow<GetCategoriesSizeState>(GetCategoriesSizeState.Loading)
    val categoriesSizeState = _categoriesSizeState.asStateFlow()

    fun updateCategoriesSize(email: String) {
        viewModelScope.launch {
            _categoriesSizeState.value = GetCategoriesSizeState.Loading
            when (val getCategoriesSizeUseCaseResult = getItemsCountByCategoriesUseCase(email)) {
                is GetCategoriesSizeUseCaseResult.ErrorUnknown -> {
                    _categoriesSizeState.value = GetCategoriesSizeState.ErrorUnknown
                }
                is GetCategoriesSizeUseCaseResult.Success -> {
                    val categoryViewList = getCategoriesSizeUseCaseResult.categoriesViewList
                    if (categoryViewList.isNotEmpty()) {
                        _categoriesSizeState.value =
                            GetCategoriesSizeState.Success(
                                getCategoriesSizeUseCaseResult.categoriesViewList
                            )
                    } else
                        _categoriesSizeState.value = GetCategoriesSizeState.NoElements
                }
            }
        }
    }

}