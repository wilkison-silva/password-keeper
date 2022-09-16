package br.com.passwordkeeper.presentation.ui.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.passwordkeeper.R
import br.com.passwordkeeper.domain.model.Categories
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
        category: Categories
    ) {
        viewModelScope.launch {
            _allCards.postValue(GetAllCardsStateResult.Loading)
            if (category == Categories.ALL) {
                when (val getAllCardsUseCaseResult = cardUseCase.getAllCards(email)) {
                    is GetAllCardsUseCaseResult.ErrorUnknown -> {
                        _allCards.postValue(GetAllCardsStateResult.ErrorUnknown)
                    }
                    is GetAllCardsUseCaseResult.Success -> {
                        _allCards.postValue(GetAllCardsStateResult.Success(getAllCardsUseCaseResult.cardViewList))
                    }
                }
            } else {
                when (val getAllCardsUseCaseResult =
                    cardUseCase.getCardsByCategory(category.name, email)) {
                    is GetCardsByCategoryUseCaseResult.ErrorUnknown -> {}
                    is GetCardsByCategoryUseCaseResult.Success -> {
                        _allCards.postValue(GetAllCardsStateResult.Success(getAllCardsUseCaseResult.cardViewList))
                    }
                }
            }
        }
    }

    fun getTitle(category: Categories) : Int{
        return when (category) {
            Categories.STREAMING -> R.string.title_streaming
            Categories.SOCIAL_MEDIA -> R.string.title_social_media
            Categories.BANKS -> R.string.title_banks
            Categories.EDUCATION -> R.string.title_education
            Categories.WORK -> R.string.title_work
            Categories.CARD -> R.string.title_cards
            Categories.ALL -> R.string.title_all_categories
        }
    }
}