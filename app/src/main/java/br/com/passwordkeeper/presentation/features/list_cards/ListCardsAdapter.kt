package br.com.passwordkeeper.presentation.features.list_cards

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.passwordkeeper.databinding.ItemListCardsBinding
import br.com.passwordkeeper.presentation.model.CardView

class ListCardsAdapter(
    private val context: Context,
    cardViewList: List<CardView> = listOf(),
    var onClickItem: (cardView: CardView) -> Unit = {}
) : RecyclerView.Adapter<ListCardsAdapter.ViewHolder>() {

    private var cardViewList = cardViewList.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListCardsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cardView: CardView = cardViewList[position]
        holder.bind(cardView)
    }

    override fun getItemCount(): Int {
        return cardViewList.size
    }

    fun updateList(listCardView: List<CardView>) {
        cardViewList.clear()
        cardViewList.addAll(listCardView)
        notifyDataSetChanged()
    }

    inner class ViewHolder(
        private val binding: ItemListCardsBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(cardView: CardView) {
            binding.textViewAllCategoriesDescription.text = cardView.description
            binding.textViewAllCategoriesTextType.text = context.getString(cardView.category)
            binding.textViewAllCategoriesDate.text = cardView.dateAsString
            binding.imageViewAllCategoriesIconHeart.setImageResource(cardView.iconHeart)
        }

    }
}