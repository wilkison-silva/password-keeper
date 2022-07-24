package br.com.passwordkeeper.domain.result.repository

import br.com.passwordkeeper.domain.model.CardData

sealed class GetAllCardsRepositoryResult {
    data class Success(val cardDataList: List<CardData>) : GetAllCardsRepositoryResult()
    object ErrorUnknown: GetAllCardsRepositoryResult()
}