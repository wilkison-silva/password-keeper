package br.com.passwordkeeper.presentation.ui.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.passwordkeeper.R
import br.com.passwordkeeper.domain.result.usecase.GetAllCardsUseCaseResult
import br.com.passwordkeeper.domain.result.usecase.GetCardsByCategoryUseCaseResult
import br.com.passwordkeeper.domain.result.usecase.GetCurrentUserUseCaseResult
import br.com.passwordkeeper.domain.result.viewmodelstate.CurrentUserState
import br.com.passwordkeeper.domain.result.viewmodelstate.GetAllCardsStateResult
import br.com.passwordkeeper.domain.result.viewmodelstate.GetCardsByCategoryStateResult
import br.com.passwordkeeper.domain.usecase.CardUseCase
import br.com.passwordkeeper.domain.usecase.SignInUseCase
import kotlinx.coroutines.launch

class ListCardsViewModel(
    private val cardUseCase: CardUseCase,
    private val signInUseCase: SignInUseCase
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

    private val _currentUserState = MutableLiveData<CurrentUserState>()
    val currentUserState: LiveData<CurrentUserState>
        get() = _currentUserState

    fun updateCurrentUser() {
        viewModelScope.launch {
            when (val signInUseCaseResult = signInUseCase.getCurrentUser()) {
                is GetCurrentUserUseCaseResult.ErrorUnknown -> {
                    _currentUserState
                        .postValue(CurrentUserState.ErrorUnknown)
                }
                is GetCurrentUserUseCaseResult.Success -> {
                    _currentUserState
                        .postValue(CurrentUserState.Success(signInUseCaseResult.userView))
                }
            }
        }
    }
}