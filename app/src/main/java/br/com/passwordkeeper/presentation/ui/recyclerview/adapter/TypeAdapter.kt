package br.com.passwordkeeper.presentation.ui.recyclerview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.passwordkeeper.databinding.HomeFragmentBinding
import br.com.passwordkeeper.databinding.ItemImageTypesBinding
import br.com.passwordkeeper.domain.model.CardType


class TypeAdapter(cardTypeList: List<CardType> = listOf()) :
    RecyclerView.Adapter<TypeAdapter.TypeViewHolder>() {
    var onClickItem: (cardType: CardType) -> Unit = {}

    private var cardTypeList = cardTypeList.toMutableList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeViewHolder {

        val binding = ItemImageTypesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TypeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TypeViewHolder, position: Int) {
        val cardType: CardType = cardTypeList[position]
        holder.bind(cardType)
    }

    override fun getItemCount(): Int {
        return cardTypeList.size
    }

    fun updateList(listCardType: List<CardType>) {
        cardTypeList.clear()
        cardTypeList.addAll(listCardType)
        notifyDataSetChanged()
    }

    inner class TypeViewHolder(
        private val binding: ItemImageTypesBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(cardType: CardType) {
            binding.textViewTextType.text = cardType.typeName
            binding.textViewQuantity.text = cardType.quantity.toString()
        }
    }
}