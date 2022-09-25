package br.com.passwordkeeper.domain.usecases.sort_cardview_list

import android.content.Context
import br.com.passwordkeeper.presentation.model.CardView

class SortCardViewListUseCaseImpl(
    private val context: Context
) : SortCardViewListUseCase {

    override fun sortByDescription(listCardsView: List<CardView>): List<CardView> {
        return listCardsView.sortedBy {
            it.description
        }
    }

    override fun sortByDate(listCardsView: List<CardView>): List<CardView> {
        return listCardsView.sortedByDescending {
            it.date
        }
    }

    override fun sortByCategory(listCardsView: List<CardView>): List<CardView> {
        return listCardsView.sortedBy {
            context.getString(it.category)
        }
    }

    override fun sortByFavorites(listCardsView: List<CardView>): List<CardView> {
        return listCardsView.sortedByDescending {
            it.favorite
        }
    }
}