package br.com.passwordkeeper.domain.usecases.sort_cardview_list

import br.com.passwordkeeper.presentation.model.CardView

interface SortCardViewListUseCase {

    fun sortByDescription(listCardsView: List<CardView>) : List<CardView>

    fun sortByDate(listCardsView: List<CardView>) : List<CardView>

    fun sortByCategory(listCardsView: List<CardView>) : List<CardView>

    fun sortByFavorites(listCardsView: List<CardView>) : List<CardView>

}