package br.com.passwordkeeper.domain.result.viewmodelstate

sealed class CreateCardStateResult {
    data class Success(val cardId: String) : CreateCardStateResult()
    object ErrorUnknown: CreateCardStateResult()
    object Loading: CreateCardStateResult()
}