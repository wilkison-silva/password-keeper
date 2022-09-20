package br.com.passwordkeeper.domain.usecase

import br.com.passwordkeeper.domain.model.CardView

interface SortCardViewListUseCase {

    fun sortByDescription(listCardsView: List<CardView>) : List<CardView>

    fun sortByDate(listCardsView: List<CardView>) : List<CardView>

    fun sortByCategory(listCardsView: List<CardView>) : List<CardView>

    fun sortByFavorites(listCardsView: List<CardView>) : List<CardView>

}