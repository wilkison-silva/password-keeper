package br.com.passwordkeeper.domain.repository

import br.com.passwordkeeper.domain.model.CardDomain

sealed class GetAllCardsRepositoryResult {
    data class Success(val cardDomainList: List<CardDomain>) : GetAllCardsRepositoryResult()
    object ErrorUnknown: GetAllCardsRepositoryResult()
}