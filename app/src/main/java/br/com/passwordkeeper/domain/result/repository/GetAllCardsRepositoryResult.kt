package br.com.passwordkeeper.domain.result.repository

import br.com.passwordkeeper.domain.model.CardDomain

sealed class GetAllCardsRepositoryResult {
    data class Success(val cardDataList: List<CardDomain>) : GetAllCardsRepositoryResult()
    object ErrorUnknown: GetAllCardsRepositoryResult()
}