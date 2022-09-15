package br.com.passwordkeeper.presentation.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.passwordkeeper.databinding.ItemImageCategoriesBinding
import br.com.passwordkeeper.domain.model.CategoryView


class CategoryAdapter(
    private val context: Context,
    categoryViewList: List<CategoryView> = listOf(),
    var onClickItem: (categoryView: CategoryView) -> Unit = {}
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private var categoryViewList = categoryViewList.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemImageCategoriesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val categoryView: CategoryView = categoryViewList[position]
        holder.bind(categoryView)
    }

    override fun getItemCount(): Int {
        return categoryViewList.size
    }

    fun updateList(listCategoryView: List<CategoryView>) {
        categoryViewList.clear()
        categoryViewList.addAll(listCategoryView)
        notifyDataSetChanged()
    }

    inner class ViewHolder(
        private val binding: ItemImageCategoriesBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(categoryView: CategoryView) {
            binding.textViewTextType.text = context.getString(categoryView.nameAsStringRes)
            binding.textViewQuantity.text = categoryView.quantity.toString()
            binding.imageViewType.setImageResource(categoryView.imageAsDrawableRes)
            binding.constraintLayoutItemCategory.setOnClickListener{
                onClickItem(categoryView)
            }
        }
    }
}