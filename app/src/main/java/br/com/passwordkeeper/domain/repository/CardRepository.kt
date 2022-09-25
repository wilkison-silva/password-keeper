package br.com.passwordkeeper.domain.repository

import br.com.passwordkeeper.commons.Categories

interface CardRepository {

    suspend fun getAllCards(email: String): GetAllCardsRepositoryResult

    suspend fun getCardById(cardId: String): GetCardByIdRepositoryResult

    suspend fun createCard(
        description: String,
        login: String,
        password: String,
        category: Categories,
        isFavorite: Boolean,
        date: String,
        emailUser: String
    ): CreateCardRepositoryResult

    suspend fun updateCard(
        cardId: String,
        description: String,
        login: String,
        password: String,
        category: Categories,
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