package br.com.passwordkeeper.domain.usecase

import br.com.passwordkeeper.domain.model.CardDomain
import br.com.passwordkeeper.domain.result.usecase.*

interface CardUseCase {

    suspend fun getAllCards(email: String): GetAllCardsUseCaseResult

    suspend fun getCardById(cardId: String): GetCardByIdUseCaseResult

    suspend fun createCard(cardDomain: CardDomain, emailUser: String): CreateCardUseCaseResult

    suspend fun updateCard(cardDomain: CardDomain, emailUser: String): UpdateCardUseCaseResult

    suspend fun deleteCard(cardId: String): DeleteCardUseCaseResult

}