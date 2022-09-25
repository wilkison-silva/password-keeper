package br.com.passwordkeeper.presentation.features.list_cards

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.passwordkeeper.R
import br.com.passwordkeeper.presentation.model.CardView
import br.com.passwordkeeper.commons.Categories
import br.com.passwordkeeper.commons.FiltersListCard
import br.com.passwordkeeper.domain.result.usecase.GetAllCardsUseCaseResult
import br.com.passwordkeeper.domain.result.usecase.GetCardsByCategoryUseCaseResult
import br.com.passwordkeeper.domain.result.viewmodelstate.GetAllCardsStateResult
import br.com.passwordkeeper.domain.usecases.CardUseCase
import br.com.passwordkeeper.domain.usecases.SortCardViewListUseCase
import kotlinx.coroutines.launch

class ListCardsViewModel(
    private val cardUseCase: CardUseCase,
    private val sortCardViewListUseCase: SortCardViewListUseCase
) : ViewModel() {

    private val _allCards = MutableLiveData<GetAllCardsStateResult>()
    val allCards: LiveData<GetAllCardsStateResult>
        get() = _allCards

    private fun getSortedCardViewList(
        filter: FiltersListCard,
        cardViewList: List<CardView>
    ): List<CardView> {
        return when (filter) {
            FiltersListCard.DESCRIPTION -> sortCardViewListUseCase.sortByDescription(cardViewList)
            FiltersListCard.DATE -> sortCardViewListUseCase.sortByDate(cardViewList)
            FiltersListCard.CATEGORY -> sortCardViewListUseCase.sortByCategory(cardViewList)
            FiltersListCard.FAVORITES -> sortCardViewListUseCase.sortByFavorites(cardViewList)
        }
    }

    private fun updateSortedCards(
        cardViewList: List<CardView>,
        filter: FiltersListCard
    ) {
        val sortedList = getSortedCardViewList(
            filter = filter,
            cardViewList = cardViewList
        )
        _allCards.postValue(GetAllCardsStateResult.Success(sortedList))
    }

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
                        updateSortedCards(
                            cardViewList = getAllCardsUseCaseResult.cardViewList,
                            filter = filter
                        )
                    }
                }
            } else {
                when (val getCardsByCategoryUseCaseResult =
                    cardUseCase.getCardsByCategory(category.name, email)) {
                    is GetCardsByCategoryUseCaseResult.ErrorUnknown -> {
                        _allCards.postValue(GetAllCardsStateResult.ErrorUnknown)
                    }
                    is GetCardsByCategoryUseCaseResult.Success -> {
                        updateSortedCards(
                            cardViewList = getCardsByCategoryUseCaseResult.cardViewList,
                            filter = filter
                        )
                    }
                }
            }
        }
    }

    fun getTitle(category: Categories): Int {
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