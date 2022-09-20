package br.com.passwordkeeper.presentation.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.passwordkeeper.R
import br.com.passwordkeeper.domain.model.CardView
import br.com.passwordkeeper.domain.model.Categories
import br.com.passwordkeeper.domain.model.FiltersListCard
import br.com.passwordkeeper.domain.result.usecase.GetAllCardsUseCaseResult
import br.com.passwordkeeper.domain.result.usecase.GetCardsByCategoryUseCaseResult
import br.com.passwordkeeper.domain.result.viewmodelstate.GetAllCardsStateResult
import br.com.passwordkeeper.domain.usecase.CardUseCase
import br.com.passwordkeeper.domain.usecase.SortCardViewListUseCase
import kotlinx.coroutines.launch

class ListCardsViewModel(
    private val cardUseCase: CardUseCase,
    private val sortCardViewListUseCase: SortCardViewListUseCase
) : ViewModel() {

    private val _allCards = MutableLiveData<GetAllCardsStateResult>()
    val allCards: LiveData<GetAllCardsStateResult>
        get() = _allCards

    fun updateCards(
        email: String,
        category: Categories,
        filter: FiltersListCard
    ) {
        viewModelScope.launch {
            _allCards.postValue(GetAllCardsStateResult.Loading)
            if (category == Categories.ALL) {
                when (val getAllCardsUseCaseResult = cardUseCase.getAllCards(email)) {
                    is GetAllCardsUseCaseResult.ErrorUnknown -> {
                        _allCards.postValue(GetAllCardsStateResult.ErrorUnknown)
                    }
                    is GetAllCardsUseCaseResult.Success -> {
                        if (filter == FiltersListCard.DESCRIPTION) {
                            val listCard = getAllCardsUseCaseResult.cardViewList
                            val sortedList = sortCardViewListUseCase.sortByDescription(listCard)
                            _allCards.postValue(GetAllCardsStateResult.Success(sortedList))
                        }
                        if (filter == FiltersListCard.DATE) {
                            val listCard = getAllCardsUseCaseResult.cardViewList
                            val sortedList = sortCardViewListUseCase.sortByDate(listCard)
                            _allCards.postValue(GetAllCardsStateResult.Success(sortedList))
                        }
                        if (filter == FiltersListCard.CATEGORY) {
                            val listCard = getAllCardsUseCaseResult.cardViewList
                            val sortedList = sortCardViewListUseCase.sortByCategory(listCard)
                            _allCards.postValue(GetAllCardsStateResult.Success(sortedList))
                        }
                        if (filter == FiltersListCard.FAVORITES) {
                            val listCard = getAllCardsUseCaseResult.cardViewList
                            val sortedList = sortCardViewListUseCase.sortByFavorites(listCard)
                            _allCards.postValue(GetAllCardsStateResult.Success(sortedList))
                        }
                    }
                }
            } else {
                when (val getAllCardsUseCaseResult =
                    cardUseCase.getCardsByCategory(category.name, email)) {
                    is GetCardsByCategoryUseCaseResult.ErrorUnknown -> {}
                    is GetCardsByCategoryUseCaseResult.Success -> {
                        if (filter == FiltersListCard.DESCRIPTION) {
                            val listCard = getAllCardsUseCaseResult.cardViewList
                            val sortedList = sortCardViewListUseCase.sortByDescription(listCard)
                            _allCards.postValue(GetAllCardsStateResult.Success(sortedList))
                        }
                        if (filter == FiltersListCard.DATE) {
                            val listCard = getAllCardsUseCaseResult.cardViewList
                            val sortedList = sortCardViewListUseCase.sortByDate(listCard)
                            _allCards.postValue(GetAllCardsStateResult.Success(sortedList))
                        }
                        if (filter == FiltersListCard.CATEGORY) {
                            val listCard = getAllCardsUseCaseResult.cardViewList
                            val sortedList = sortCardViewListUseCase.sortByCategory(listCard)
                            _allCards.postValue(GetAllCardsStateResult.Success(sortedList))
                        }
                        if (filter == FiltersListCard.FAVORITES) {
                            val listCard = getAllCardsUseCaseResult.cardViewList
                            val sortedList = sortCardViewListUseCase.sortByFavorites(listCard)
                            _allCards.postValue(GetAllCardsStateResult.Success(sortedList))
                        }
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