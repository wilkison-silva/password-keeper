package br.com.passwordkeeper.domain.model

import br.com.passwordkeeper.R

data class CategoryDomain(
    val category: Categories,
    val quantity: Int,
) {

    fun convertToCategoryView(): CategoryView {
        return CategoryView(
            nameAsStringRes = getNameForCategory(category),
            quantity = quantity,
            imageAsDrawableRes = getIconForCategory(category)
        )
    }

    private fun getNameForCategory(category: Categories): Int {
        return when (category) {
            Categories.STREAMING_TYPE -> R.string.streaming
            Categories.SOCIAL_MEDIA_TYPE -> R.string.social_media
            Categories.BANKS_TYPE -> R.string.banks
            Categories.EDUCATION_TYPE -> R.string.education
            Categories.WORK_TYPE -> R.string.work
            Categories.CARD_TYPE -> R.string.cards
        }
    }

    private fun getIconForCategory(category: Categories): Int {
        return when (category) {
            Categories.STREAMING_TYPE -> R.drawable.ic_stream_type
            Categories.SOCIAL_MEDIA_TYPE -> R.drawable.ic_social_media
            Categories.BANKS_TYPE -> R.drawable.ic_bank
            Categories.EDUCATION_TYPE -> R.drawable.ic_education
            Categories.WORK_TYPE -> R.drawable.ic_work
            Categories.CARD_TYPE -> R.drawable.ic_card
        }
    }

}