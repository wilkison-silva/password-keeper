package br.com.passwordkeeper.data.repository

import br.com.passwordkeeper.domain.model.CardData
import br.com.passwordkeeper.domain.result.repository.*

interface CardRepository {

    suspend fun getAllCards(email: String): GetAllCardsRepositoryResult

    suspend fun getCardById(cardId: String): GetCardByIdRepositoryResult

    suspend fun createCard(cardData: CardData, emailUser: String): CreateCardRepositoryResult

    suspend fun updateCard(cardData: CardData, emailUser: String): UpdateCardRepositoryResult

    suspend fun deleteCard(cardId: String): DeleteCardRepositoryResult

    suspend fun getFavorites(email: String): GetFavoriteCardsRepositoryResult

    suspend fun getCategories(email: String): GetFavoriteCardsRepositoryResult
}