package br.com.passwordkeeper.presentation.features.home

import android.text.SpannableStringBuilder
import androidx.core.text.bold
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.passwordkeeper.presentation.model.AdviceView
import br.com.passwordkeeper.domain.result.usecase.GetAdviceUseCaseResult
import br.com.passwordkeeper.domain.result.usecase.GetCategoriesSizeUseCaseResult
import br.com.passwordkeeper.domain.result.usecase.GetFavoriteCardsUseCaseResult
import br.com.passwordkeeper.domain.result.viewmodelstate.GetAdviceStateResult
import br.com.passwordkeeper.domain.result.viewmodelstate.GetCategoriesSizeStateResult
import br.com.passwordkeeper.domain.result.viewmodelstate.GetFavoriteCardsStateResult
import br.com.passwordkeeper.domain.usecases.get_advice.GetAdviceUseCase
import br.com.passwordkeeper.domain.usecases.sort_cardview_list.SortCardViewListUseCase
import br.com.passwordkeeper.domain.usecases.get_favorites_cards.GetFavoriteCardsUseCase
import br.com.passwordkeeper.domain.usecases.get_items_count_by_categories.GetItemsCountByCategoriesUseCase
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getAdviceUseCase: GetAdviceUseCase,
    private val getFavoriteCardsUseCase: GetFavoriteCardsUseCase,
    private val getItemsCountByCategoriesUseCase: GetItemsCountByCategoriesUseCase,
    private val sortCardViewListUseCase: SortCardViewListUseCase
) : ViewModel() {

    private val _adviceState = MutableLiveData<GetAdviceStateResult>()
    val adviceState: LiveData<GetAdviceStateResult>
        get() = _adviceState

    fun updateAdvice() {
        _adviceState.value ?: getAdvice()
    }

    private fun getAdvice() {
        viewModelScope.launch {
            _adviceState.postValue(GetAdviceStateResult.Loading)
            when (val getAdviceUseCaseResult = getAdviceUseCase()) {
                is GetAdviceUseCaseResult.Success -> {
                    _adviceState.postValue(GetAdviceStateResult.Success(getAdviceUseCaseResult.adviceView))
                }
                is GetAdviceUseCaseResult.SuccessAdviceWithoutMessage -> {
                    _adviceState.postValue(GetAdviceStateResult.SuccessWithoutMessage)
                }
                is GetAdviceUseCaseResult.ErrorUnknown -> {
                    _adviceState.postValue(GetAdviceStateResult.ErrorUnknown)
                }
            }
        }
    }

    fun getAdviceWithFirstLetterBold(adviceView: AdviceView): SpannableStringBuilder {
        return SpannableStringBuilder()
            .bold { append(adviceView.firstLetter) }
            .append(adviceView.advice.substring(1))
    }

    private val _favoriteCardsState = MutableLiveData<GetFavoriteCardsStateResult>()
    val favoriteCardsState: LiveData<GetFavoriteCardsStateResult>
        get() = _favoriteCardsState

    fun updateFavoriteCards(email: String) {
        viewModelScope.launch {
            _favoriteCardsState.postValue(GetFavoriteCardsStateResult.Loading)
            when (val getFavoriteCardsUseCaseResult = getFavoriteCardsUseCase(email)) {
                is GetFavoriteCardsUseCaseResult.ErrorUnknown -> {
                    _favoriteCardsState.postValue(GetFavoriteCardsStateResult.ErrorUnknown)
                }
                is GetFavoriteCardsUseCaseResult.Success -> {
                    val cardViewList = getFavoriteCardsUseCaseResult.cardViewList
                    if (cardViewList.isNotEmpty()) {
                        val sortedList = sortCardViewListUseCase.sortByDate(cardViewList)
                        _favoriteCardsState.postValue(
                            GetFavoriteCardsStateResult.Success(
                               sortedList
                            )
                        )
                    } else
                        _favoriteCardsState.postValue(GetFavoriteCardsStateResult.NoElements)
                }
            }
        }
    }

    private val _categoriesSizeState = MutableLiveData<GetCategoriesSizeStateResult>()
    val categoriesSizeState: LiveData<GetCategoriesSizeStateResult>
        get() = _categoriesSizeState

    fun updateCategoriesSize(email: String) {
        viewModelScope.launch {
            _categoriesSizeState.postValue(GetCategoriesSizeStateResult.Loading)
            when (val getCategoriesSizeUseCaseResult = getItemsCountByCategoriesUseCase(email)) {
                is GetCategoriesSizeUseCaseResult.ErrorUnknown -> {
                    _categoriesSizeState.postValue(GetCategoriesSizeStateResult.ErrorUnknown)
                }
                is GetCategoriesSizeUseCaseResult.Success -> {
                    val categoryViewList = getCategoriesSizeUseCaseResult.categoriesViewList
                    if (categoryViewList.isNotEmpty()) {
                        _categoriesSizeState.postValue(
                            GetCategoriesSizeStateResult.Success(
                                getCategoriesSizeUseCaseResult.categoriesViewList
                            )
                        )
                    } else
                        _categoriesSizeState.postValue(GetCategoriesSizeStateResult.NoElements)
                }
            }
        }
    }

}