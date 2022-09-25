package br.com.passwordkeeper.domain.repository

import br.com.passwordkeeper.domain.model.CardDomain

sealed class GetCardByIdRepositoryResult {
    data class Success(val cardDomain: CardDomain) : GetCardByIdRepositoryResult()
    object ErrorUnknown: GetCardByIdRepositoryResult()
}