package br.com.passwordkeeper.domain.usecase

import br.com.passwordkeeper.domain.model.CardData
import br.com.passwordkeeper.domain.model.CardDomain
import br.com.passwordkeeper.domain.result.usecase.CreateCardUseCaseResult
import br.com.passwordkeeper.domain.result.usecase.GetAllCardsUseCaseResult
import br.com.passwordkeeper.domain.result.usecase.GetCardByIdUseCaseResult

interface CardUseCase {

    suspend fun getAllCards(email: String): GetAllCardsUseCaseResult

    suspend fun getCardById(cardId: String): GetCardByIdUseCaseResult

    suspend fun createCard(cardDomain: CardDomain, emailUser: String): CreateCardUseCaseResult
//
//    suspend fun updateCard(cardData: CardData, emailUser: String): UpdateCardRepositoryResult
//
//    suspend fun deleteCard(cardId: String): DeleteCardRepositoryResult

}