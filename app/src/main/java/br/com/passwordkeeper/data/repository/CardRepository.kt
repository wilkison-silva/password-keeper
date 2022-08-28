package br.com.passwordkeeper.data.repository

import br.com.passwordkeeper.domain.result.repository.*

interface CardRepository {

    suspend fun getAllCards(email: String): GetAllCardsRepositoryResult

    suspend fun getCardById(cardId: String): GetCardByIdRepositoryResult

    suspend fun createCard(
        description: String,
        login: String,
        password: String,
        category: String,
        isFavorite: Boolean,
        date: String,
        emailUser: String
    ): CreateCardRepositoryResult

    suspend fun updateCard(
        cardId: String,
        description: String,
        login: String,
        password: String,
        category: String,
        isFavorite: Boolean,
        date: String,
        emailUser: String
    ): UpdateCardRepositoryResult

    suspend fun deleteCard(cardId: String): DeleteCardRepositoryResult

    suspend fun getFavorites(email: String): GetFavoriteCardsRepositoryResult

    suspend fun getCardsByCategory(
        category: String,
        email: String,
    ): GetCardsByCategoryRepositoryResult

}