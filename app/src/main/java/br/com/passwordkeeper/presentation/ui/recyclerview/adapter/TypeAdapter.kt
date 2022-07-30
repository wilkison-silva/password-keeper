package br.com.passwordkeeper.presentation.ui.recyclerview.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.passwordkeeper.databinding.ItemImageTypesBinding
import br.com.passwordkeeper.domain.model.CategoryView


class TypeAdapter(
    private val context: Context,
    categoryViewList: List<CategoryView> = listOf()
) :
    RecyclerView.Adapter<TypeAdapter.TypeViewHolder>() {
    var onClickItem: (categoryView: CategoryView) -> Unit = {}

    private var cardTypeList = categoryViewList.toMutableList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeViewHolder {
        val binding = ItemImageTypesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TypeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TypeViewHolder, position: Int) {
        val categoryView: CategoryView = cardTypeList[position]
        holder.bind(categoryView)
    }

    override fun getItemCount(): Int {
        return cardTypeList.size
    }

    fun updateList(listCategoryView: List<CategoryView>) {
        cardTypeList.clear()
        cardTypeList.addAll(listCategoryView)
        notifyDataSetChanged()
    }

    inner class TypeViewHolder(
        private val binding: ItemImageTypesBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(categoryView: CategoryView) {
            binding.textViewTextType.text = context.getString(categoryView.StringResourceId)
            binding.textViewQuantity.text = categoryView.size.toString()
            binding.imageViewType.setImageResource(categoryView.DrawableResourceId)
        }
    }
}