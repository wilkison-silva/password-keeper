package br.com.passwordkeeper.domain.result.repository

import br.com.passwordkeeper.domain.model.CardDomain

sealed class GetCategoriesRepositoryResult {
    data class Success(val cardDataList: List<CardDomain>) : GetCategoriesRepositoryResult()
    object ErrorUnknown: GetCategoriesRepositoryResult()
}