package br.com.passwordkeeper.presentation.ui.recyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.passwordkeeper.databinding.ItemImageAllCategoriesBinding
import br.com.passwordkeeper.domain.model.CardView

class AllCategoriesAdapter(
    cardViewList: List<CardView> = listOf(),
    var onClickItem: (cardView: CardView) -> Unit = {}
) : RecyclerView.Adapter<AllCategoriesAdapter.ViewHolder>() {

    private var cardViewList = cardViewList.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllCategoriesAdapter.ViewHolder {
        val binding = ItemImageAllCategoriesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AllCategoriesAdapter.ViewHolder, position: Int) {
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
        private val binding: ItemImageAllCategoriesBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(cardView: CardView) {
            binding.textViewAllCategoriesDescription.text = cardView.description
            binding.textViewAllCategoriesTextType.text = cardView.category
            binding.textViewAllCategoriesDate.text = cardView.date
        }
    }
}