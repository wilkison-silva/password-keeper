package br.com.passwordkeeper.data.repository

import br.com.passwordkeeper.domain.result.repository.GetAllCardsRepositoryResult
import br.com.passwordkeeper.domain.result.repository.GetCardByIdRepositoryResult

interface CardRepository {

    suspend fun getAllCards(email: String): GetAllCardsRepositoryResult

    suspend fun getCardById(cardId: String): GetCardByIdRepositoryResult
}