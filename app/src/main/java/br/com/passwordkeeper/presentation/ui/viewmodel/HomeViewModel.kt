package br.com.passwordkeeper.presentation.ui.viewmodel

import android.text.SpannableStringBuilder
import androidx.core.text.bold
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.passwordkeeper.domain.model.AdviceView
import br.com.passwordkeeper.domain.result.usecase.GetAdviceUseCaseResult
import br.com.passwordkeeper.domain.result.usecase.GetCategoriesSizeUseCaseResult
import br.com.passwordkeeper.domain.result.usecase.GetFavoriteCardsUseCaseResult
import br.com.passwordkeeper.domain.result.viewmodelstate.GetAdviceStateResult
import br.com.passwordkeeper.domain.result.viewmodelstate.GetCategoriesSizeStateResult
import br.com.passwordkeeper.domain.result.viewmodelstate.GetFavoriteCardsStateResult
import br.com.passwordkeeper.domain.usecase.AdviceUseCase
import br.com.passwordkeeper.domain.usecase.CardUseCase
import br.com.passwordkeeper.domain.usecase.SortCardViewListUseCase
import kotlinx.coroutines.launch

class HomeViewModel(
    private val adviceUseCase: AdviceUseCase,
    private val cardUseCase: CardUseCase,
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
            when (val getAdviceUseCaseResult = adviceUseCase.getAdvice()) {
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
            when (val getFavoriteCardsUseCaseResult = cardUseCase.getFavorites(email)) {
                is GetFavoriteCardsUseCaseResult.ErrorUnknown -> {
                    _favoriteCardsState.postValue(GetFavoriteCardsStateResult.ErrorUnknown)
                }
                is GetFavoriteCardsUseCaseResult.Success -> {
                    val cardViewList = getFavoriteCardsUseCaseResult.cardViewList
                    if (cardViewList.isNotEmpty()) {
                        val favoriteCardList = sortCardViewListUseCase.sortByFavorites(cardViewList)
                        val sortedList = favoriteCardList.sortedByDescending {
                            it.date
                        }
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
            when (val getCategoriesSizeUseCaseResult = cardUseCase.getCategoriesSize(email)) {
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