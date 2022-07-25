package br.com.passwordkeeper.data.repository

import br.com.passwordkeeper.domain.model.CardDomain
import br.com.passwordkeeper.domain.result.repository.*

interface CardRepository {

    suspend fun getAllCards(email: String): GetAllCardsRepositoryResult

    suspend fun getCardById(cardId: String): GetCardByIdRepositoryResult

    suspend fun createCard(cardDomain: CardDomain, emailUser: String): CreateCardRepositoryResult

    suspend fun updateCard(cardDomain: CardDomain, emailUser: String): UpdateCardRepositoryResult

    suspend fun deleteCard(cardId: String): DeleteCardRepositoryResult
}