package br.com.passwordkeeper.domain.usecase

import br.com.passwordkeeper.domain.model.Categories
import br.com.passwordkeeper.domain.result.usecase.*

interface CardUseCase {

    suspend fun getAllCards(email: String): GetAllCardsUseCaseResult

    suspend fun getCardById(cardId: String): GetCardByIdUseCaseResult

    suspend fun createCard(
        description: String,
        login: String,
        password: String,
        category: Categories,
        isFavorite: Boolean,
        date: String,
        emailUser: String
    ): CreateCardUseCaseResult

    suspend fun updateCard(
        cardId: String,
        description: String,
        login: String,
        password: String,
        category: String,
        isFavorite: Boolean,
        date: String,
        emailUser: String
    ): UpdateCardUseCaseResult

    suspend fun deleteCard(cardId: String): DeleteCardUseCaseResult

    suspend fun getFavorites(email: String): GetFavoriteCardsUseCaseResult

    suspend fun getCategoriesSize(email: String): GetCategoriesSizeUseCaseResult

    suspend fun getCardsByCategory(category: String, email: String): GetCardsByCategoryUseCaseResult
}