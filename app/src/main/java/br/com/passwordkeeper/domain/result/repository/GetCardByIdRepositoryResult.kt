package br.com.passwordkeeper.domain.result.repository

import br.com.passwordkeeper.domain.model.CardData

sealed class GetCardByIdRepositoryResult {
    data class Success(val cardData: CardData) : GetCardByIdRepositoryResult()
    object ErrorUnknown: GetCardByIdRepositoryResult()
}