package br.com.passwordkeeper.domain.result.repository

import br.com.passwordkeeper.domain.model.CardDomain

sealed class GetFavoriteCardsRepositoryResult {
    data class Success(val cardDomainList: List<CardDomain>) : GetFavoriteCardsRepositoryResult()
    object ErrorUnknown: GetFavoriteCardsRepositoryResult()
}