package br.com.passwordkeeper.domain.result.viewmodelstate

sealed class FormValidationCardStateResult {
    data class Success(
        val description: String,
        val login: String,
        val password: String,
        val category: String,
        val isFavorite: Boolean,
        val date: String
    ) : FormValidationCardStateResult()
    object DescriptionIsEmpty: FormValidationCardStateResult()
    object CategoryNotSelected: FormValidationCardStateResult()
}