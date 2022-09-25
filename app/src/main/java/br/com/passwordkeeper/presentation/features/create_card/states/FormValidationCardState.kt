package br.com.passwordkeeper.presentation.features.create_card.states

sealed class FormValidationCardState {
    data class Success(
        val description: String,
        val login: String,
        val password: String,
        val category: String,
        val isFavorite: Boolean,
        val date: String
    ) : FormValidationCardState()
    object DescriptionIsEmpty: FormValidationCardState()
    object CategoryNotSelected: FormValidationCardState()
}