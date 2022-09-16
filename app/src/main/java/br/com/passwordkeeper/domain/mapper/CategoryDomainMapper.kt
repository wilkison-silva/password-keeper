package br.com.passwordkeeper.domain.mapper

import br.com.passwordkeeper.R
import br.com.passwordkeeper.domain.model.Categories
import br.com.passwordkeeper.domain.model.CategoryDomain
import br.com.passwordkeeper.domain.model.CategoryView

class CategoryDomainMapper : BaseMapper<CategoryDomain, CategoryView>() {

    override fun transform(model: CategoryDomain): CategoryView {
        return CategoryView(
            category = model.category,
            nameAsStringRes = getNameForCategory(model.category),
            quantity = model.quantity,
            imageAsDrawableRes = getIconForCategory(model.category)
        )
    }

    private fun getNameForCategory(category: Categories): Int {
        return when (category) {
            Categories.STREAMING -> R.string.streaming
            Categories.SOCIAL_MEDIA -> R.string.social_media
            Categories.BANKS -> R.string.banks
            Categories.EDUCATION -> R.string.education
            Categories.WORK -> R.string.work
            Categories.CARD -> R.string.cards
            Categories.ALL -> R.string.unknown_category
        }
    }

    private fun getIconForCategory(category: Categories): Int {
        return when (category) {
            Categories.STREAMING -> R.drawable.ic_stream_type
            Categories.SOCIAL_MEDIA -> R.drawable.ic_social_media
            Categories.BANKS -> R.drawable.ic_bank
            Categories.EDUCATION -> R.drawable.ic_education
            Categories.WORK -> R.drawable.ic_work
            Categories.CARD -> R.drawable.ic_card
            Categories.ALL -> R.drawable.ic_404_not_found
        }
    }


}