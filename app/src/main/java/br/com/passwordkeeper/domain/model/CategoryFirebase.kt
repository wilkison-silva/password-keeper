package br.com.passwordkeeper.domain.model

data class CategoryFirebase(
    val typeName: String,
    val size: Int
) {

    fun convertToCategoryData(): CategoryData {
        return CategoryData(
            typeName = typeName,
            size = size
        )
    }

}