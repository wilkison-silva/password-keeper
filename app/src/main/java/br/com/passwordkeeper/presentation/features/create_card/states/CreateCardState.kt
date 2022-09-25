package br.com.passwordkeeper.presentation.features.create_card.states

sealed class CreateCardState {
    data class Success(val cardId: String) : CreateCardState()
    object ErrorUnknown: CreateCardState()
    object Loading: CreateCardState()
}