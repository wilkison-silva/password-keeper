package br.com.passwordkeeper.domain.model

data class CategoryData(
    val typeName: String,
    val size: Int
) {

    fun convertToCategoryDomain(): CategoryDomain {
        return CategoryDomain(
            typeName = typeName,
            size = size
        )
    }

}