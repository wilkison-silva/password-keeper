package br.com.passwordkeeper.presentation.features.list_cards

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.passwordkeeper.R
import br.com.passwordkeeper.presentation.model.CardView
import br.com.passwordkeeper.commons.Categories
import br.com.passwordkeeper.commons.FiltersListCard
import br.com.passwordkeeper.domain.usecases.get_all_cards.GetAllCardsUseCaseResult
import br.com.passwordkeeper.domain.usecases.get_cards_by_category.GetCardsByCategoryUseCaseResult
import br.com.passwordkeeper.domain.usecases.sort_cardview_list.SortCardViewListUseCase
import br.com.passwordkeeper.domain.usecases.get_all_cards.GetAllCardsUseCase
import br.com.passwordkeeper.domain.usecases.get_cards_by_category.GetCardsByCategoryUseCase
import br.com.passwordkeeper.presentation.features.list_cards.states.GetCardsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ListCardsViewModel(
    private val getAllCardsUseCase: GetAllCardsUseCase,
    private val getCardsByCategoryUseCase: GetCardsByCategoryUseCase,
    private val sortCardViewListUseCase: SortCardViewListUseCase
) : ViewModel() {

    private val _cardsListState = MutableStateFlow<GetCardsState>(GetCardsState.EmptyState)
    val cardsListState = _cardsListState.asStateFlow()

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
        _cardsListState.value = GetCardsState.Success(sortedList)
    }

    private val _resultsForSearchingState = MutableStateFlow<List<CardView>>(listOf())
    val resultsForSearchingState = _resultsForSearchingState.asStateFlow()

    fun searchByDescription(description: String) {
        if (_cardsListState.value is GetCardsState.Success) {
            val list = (_cardsListState.value as GetCardsState.Success).cardViewList
            val result = list.filter {
                it.description.contains(description)
            }
            _resultsForSearchingState.value = result
        }
    }

    fun updateCards(
        email: String,
        category: Categories,
        filter: FiltersListCard
    ) {
        viewModelScope.launch {
            _cardsListState.value = GetCardsState.Loading
            if (category == Categories.ALL) {
                when (val getAllCardsUseCaseResult = getAllCardsUseCase(email)) {
                    is GetAllCardsUseCaseResult.ErrorUnknown -> {
                        _cardsListState.value = GetCardsState.ErrorUnknown
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
                    getCardsByCategoryUseCase(category.name, email)) {
                    is GetCardsByCategoryUseCaseResult.ErrorUnknown -> {
                        _cardsListState.value = GetCardsState.ErrorUnknown
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
            Categories.ALL, Categories.NONE -> R.string.title_all_categories
        }
    }
}