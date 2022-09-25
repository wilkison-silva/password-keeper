package br.com.passwordkeeper.presentation.features.home

import android.text.SpannableStringBuilder
import androidx.core.text.bold
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.passwordkeeper.presentation.model.AdviceView
import br.com.passwordkeeper.domain.usecases.get_advice.GetAdviceUseCaseResult
import br.com.passwordkeeper.domain.usecases.get_items_count_by_categories.GetCategoriesSizeUseCaseResult
import br.com.passwordkeeper.domain.usecases.get_favorites_cards.GetFavoriteCardsUseCaseResult
import br.com.passwordkeeper.domain.usecases.get_advice.GetAdviceUseCase
import br.com.passwordkeeper.domain.usecases.sort_cardview_list.SortCardViewListUseCase
import br.com.passwordkeeper.domain.usecases.get_favorites_cards.GetFavoriteCardsUseCase
import br.com.passwordkeeper.domain.usecases.get_items_count_by_categories.GetItemsCountByCategoriesUseCase
import br.com.passwordkeeper.presentation.features.home.states.GetAdviceState
import br.com.passwordkeeper.presentation.features.home.states.GetCategoriesSizeState
import br.com.passwordkeeper.presentation.features.home.states.GetFavoriteCardsState
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getAdviceUseCase: GetAdviceUseCase,
    private val getFavoriteCardsUseCase: GetFavoriteCardsUseCase,
    private val getItemsCountByCategoriesUseCase: GetItemsCountByCategoriesUseCase,
    private val sortCardViewListUseCase: SortCardViewListUseCase
) : ViewModel() {

    private val _adviceState = MutableLiveData<GetAdviceState>()
    val adviceState: LiveData<GetAdviceState>
        get() = _adviceState

    fun updateAdvice() {
        _adviceState.value ?: getAdvice()
    }

    private fun getAdvice() {
        viewModelScope.launch {
            _adviceState.postValue(GetAdviceState.Loading)
            when (val getAdviceUseCaseResult = getAdviceUseCase()) {
                is GetAdviceUseCaseResult.Success -> {
                    _adviceState.postValue(GetAdviceState.Success(getAdviceUseCaseResult.adviceView))
                }
                is GetAdviceUseCaseResult.SuccessAdviceWithoutMessage -> {
                    _adviceState.postValue(GetAdviceState.SuccessWithoutMessage)
                }
                is GetAdviceUseCaseResult.ErrorUnknown -> {
                    _adviceState.postValue(GetAdviceState.ErrorUnknown)
                }
            }
        }
    }

    fun getAdviceWithFirstLetterBold(adviceView: AdviceView): SpannableStringBuilder {
        return SpannableStringBuilder()
            .bold { append(adviceView.firstLetter) }
            .append(adviceView.advice.substring(1))
    }

    private val _favoriteCardsState = MutableLiveData<GetFavoriteCardsState>()
    val favoriteCardsState: LiveData<GetFavoriteCardsState>
        get() = _favoriteCardsState

    fun updateFavoriteCards(email: String) {
        viewModelScope.launch {
            _favoriteCardsState.postValue(GetFavoriteCardsState.Loading)
            when (val getFavoriteCardsUseCaseResult = getFavoriteCardsUseCase(email)) {
                is GetFavoriteCardsUseCaseResult.ErrorUnknown -> {
                    _favoriteCardsState.postValue(GetFavoriteCardsState.ErrorUnknown)
                }
                is GetFavoriteCardsUseCaseResult.Success -> {
                    val cardViewList = getFavoriteCardsUseCaseResult.cardViewList
                    if (cardViewList.isNotEmpty()) {
                        val sortedList = sortCardViewListUseCase.sortByDate(cardViewList)
                        _favoriteCardsState.postValue(
                            GetFavoriteCardsState.Success(
                               sortedList
                            )
                        )
                    } else
                        _favoriteCardsState.postValue(GetFavoriteCardsState.NoElements)
                }
            }
        }
    }

    private val _categoriesSizeState = MutableLiveData<GetCategoriesSizeState>()
    val categoriesSizeState: LiveData<GetCategoriesSizeState>
        get() = _categoriesSizeState

    fun updateCategoriesSize(email: String) {
        viewModelScope.launch {
            _categoriesSizeState.postValue(GetCategoriesSizeState.Loading)
            when (val getCategoriesSizeUseCaseResult = getItemsCountByCategoriesUseCase(email)) {
                is GetCategoriesSizeUseCaseResult.ErrorUnknown -> {
                    _categoriesSizeState.postValue(GetCategoriesSizeState.ErrorUnknown)
                }
                is GetCategoriesSizeUseCaseResult.Success -> {
                    val categoryViewList = getCategoriesSizeUseCaseResult.categoriesViewList
                    if (categoryViewList.isNotEmpty()) {
                        _categoriesSizeState.postValue(
                            GetCategoriesSizeState.Success(
                                getCategoriesSizeUseCaseResult.categoriesViewList
                            )
                        )
                    } else
                        _categoriesSizeState.postValue(GetCategoriesSizeState.NoElements)
                }
            }
        }
    }

}