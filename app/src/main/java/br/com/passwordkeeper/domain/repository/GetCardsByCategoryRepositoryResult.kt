package br.com.passwordkeeper.domain.repository

import br.com.passwordkeeper.domain.model.CardDomain

sealed class GetCardsByCategoryRepositoryResult {
    data class Success(val cardDomainList: List<CardDomain>) : GetCardsByCategoryRepositoryResult()
    object ErrorUnknown : GetCardsByCategoryRepositoryResult()
}