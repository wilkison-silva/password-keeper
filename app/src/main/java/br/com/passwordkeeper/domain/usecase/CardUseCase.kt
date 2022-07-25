package br.com.passwordkeeper.domain.usecase

import br.com.passwordkeeper.domain.result.usecase.GetAllCardsUseCaseResult

interface CardUseCase {

    suspend fun getAllCards(email: String): GetAllCardsUseCaseResult

//    suspend fun getCardById(cardId: String): GetCardByIdRepositoryResult
//
//    suspend fun createCard(cardData: CardData, emailUser: String): CreateCardRepositoryResult
//
//    suspend fun updateCard(cardData: CardData, emailUser: String): UpdateCardRepositoryResult
//
//    suspend fun deleteCard(cardId: String): DeleteCardRepositoryResult

}