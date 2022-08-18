package br.com.passwordkeeper.presentation.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.passwordkeeper.databinding.ItemImageFavoriteBinding
import br.com.passwordkeeper.domain.model.CardView

class FavoriteAdapter(
    private val context: Context,
    cardViewList: List<CardView> = listOf(),
    var onClickItem: (cardView: CardView) -> Unit = {}
) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    private var cardViewList = cardViewList.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapter.ViewHolder {
        val binding = ItemImageFavoriteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteAdapter.ViewHolder, position: Int) {
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
        private val binding: ItemImageFavoriteBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(cardView: CardView) {
            binding.textViewDescription.text = cardView.description
            binding.textViewTextType.text = cardView.category
            binding.textViewDate.text = cardView.date
        }
    }
}