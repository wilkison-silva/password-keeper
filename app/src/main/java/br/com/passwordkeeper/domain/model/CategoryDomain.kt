package br.com.passwordkeeper.domain.model

import br.com.passwordkeeper.R

private const val STREAMING_TYPE = "Streaming"
private const val SOCIAL_MEDIA_TYPE = "Social Media"
private const val BANKS_TYPE = "Banks"
private const val EDUCATION_TYPE = "Education"
private const val WORK_TYPE = "Work"
private const val CARD_TYPE = "Card"

data class CategoryDomain(
    val typeName: String,
    val size: Int,
) {

    fun convertToCategoryView(): CategoryView {
        return CategoryView(
            typeName = typeName,
            size = size,
            icon = getIconForCategory(typeName)
        )
    }

    private fun getIconForCategory(typeName: String): Int {
        return when (typeName) {
            STREAMING_TYPE -> R.drawable.ic_stream_type
            SOCIAL_MEDIA_TYPE ->R.drawable.ic_social_media
            BANKS_TYPE -> R.drawable.ic_bank
            EDUCATION_TYPE -> R.drawable.ic_education
            WORK_TYPE -> R.drawable.ic_work
            CARD_TYPE -> R.drawable.ic_card
            else -> R.drawable.ic_404_not_found
        }
    }

}