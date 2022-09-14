package br.com.passwordkeeper.presentation.ui.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.passwordkeeper.R
import br.com.passwordkeeper.domain.result.usecase.GetAllCardsUseCaseResult
import br.com.passwordkeeper.domain.result.usecase.GetCardsByCategoryUseCaseResult
import br.com.passwordkeeper.domain.result.viewmodelstate.GetAllCardsStateResult
import br.com.passwordkeeper.domain.usecase.CardUseCase
import kotlinx.coroutines.launch

class ListCardsViewModel(
    private val cardUseCase: CardUseCase
) : ViewModel() {

    private val _allCards = MutableLiveData<GetAllCardsStateResult>()
    val allCards: LiveData<GetAllCardsStateResult>
        get() = _allCards

    fun updateCards(
        email: String,
        @StringRes titleRes: Int,
        title: String
    ) {
        viewModelScope.launch {
            _allCards.postValue(GetAllCardsStateResult.Loading)
            if (titleRes == R.string.title_all_categories) {
                when (val getAllCardsUseCaseResult = cardUseCase.getAllCards(email)) {
                    is GetAllCardsUseCaseResult.ErrorUnknown -> {
                        _allCards.postValue(GetAllCardsStateResult.ErrorUnknown)
                    }
                    is GetAllCardsUseCaseResult.Success -> {
                        _allCards.postValue(GetAllCardsStateResult.Success(getAllCardsUseCaseResult.cardViewList))
                    }
                }
            } else {
                when (val getAllCardsUseCaseResult = cardUseCase.getCardsByCategory(title, email)) {
                    is GetCardsByCategoryUseCaseResult.ErrorUnknown -> {}
                    is GetCardsByCategoryUseCaseResult.Success -> {
                        _allCards.postValue(GetAllCardsStateResult.Success(getAllCardsUseCaseResult.cardViewList))
                    }
                }
            }
        }
    }
}