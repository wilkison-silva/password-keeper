package br.com.passwordkeeper.domain.usecase

import br.com.passwordkeeper.data.repository.CardRepository
import br.com.passwordkeeper.domain.model.*
import br.com.passwordkeeper.domain.result.repository.*
import br.com.passwordkeeper.domain.result.usecase.*

class FirebaseCardUseCaseImpl(
    private val cardRepository: CardRepository,
) : CardUseCase {

    override suspend fun getAllCards(email: String): GetAllCardsUseCaseResult {
        return when (val getAllCardsRepositoryResult = cardRepository.getAllCards(email)) {
            is GetAllCardsRepositoryResult.Success -> {
                val cardDomainList = getAllCardsRepositoryResult.cardDomainList
                val cardViewList = cardDomainList.map { cardDomain: CardDomain ->
                    cardDomain.convertToCardView()
                }
                GetAllCardsUseCaseResult.SuccessWithCards(cardViewList)
            }
            is GetAllCardsRepositoryResult.ErrorUnknown -> {
                GetAllCardsUseCaseResult.ErrorUnknown
            }
        }
    }

    override suspend fun getCardById(cardId: String): GetCardByIdUseCaseResult {
        return when (val getCardByIdRepositoryResult = cardRepository.getCardById(cardId)) {
            is GetCardByIdRepositoryResult.Success -> {
                val cardView = getCardByIdRepositoryResult.cardDomain.convertToCardView()
                GetCardByIdUseCaseResult.Success(cardView)
            }
            is GetCardByIdRepositoryResult.ErrorUnknown -> {
                GetCardByIdUseCaseResult.ErrorUnknown
            }
        }
    }

    override suspend fun createCard(
        cardDomain: CardDomain,
        emailUser: String,
    ): CreateCardUseCaseResult {
        return when (val createCardRepositoryResult =
            cardRepository.createCard(cardDomain.convertToCardData(), emailUser)) {
            is CreateCardRepositoryResult.Success -> {
                val cardId = createCardRepositoryResult.cardId
                CreateCardUseCaseResult.Success(cardId)
            }
            is CreateCardRepositoryResult.ErrorUnknown -> {
                CreateCardUseCaseResult.ErrorUnknown
            }
        }
    }

    override suspend fun updateCard(
        cardDomain: CardDomain,
        emailUser: String,
    ): UpdateCardUseCaseResult {
        return when (val updateCardUseCaseResult =
            cardRepository.updateCard(cardDomain.convertToCardData(), emailUser)) {
            is UpdateCardRepositoryResult.Success -> {
                val cardId = updateCardUseCaseResult.cardId
                UpdateCardUseCaseResult.Success(cardId)
            }
            is UpdateCardRepositoryResult.ErrorUnknown -> {
                UpdateCardUseCaseResult.ErrorUnknown
            }
        }
    }

    override suspend fun deleteCard(cardId: String): DeleteCardUseCaseResult {
        return when (cardRepository.deleteCard(cardId)) {
            is DeleteCardRepositoryResult.Success -> {
                DeleteCardUseCaseResult.Success
            }
            is DeleteCardRepositoryResult.ErrorUnknown -> {
                DeleteCardUseCaseResult.ErrorUnknown
            }
        }
    }

    override suspend fun getFavorites(email: String): GetFavoriteCardsUseCaseResult {
        return when (val getFavoriteCardsRepositoryResult = cardRepository.getFavorites(email)) {
            is GetFavoriteCardsRepositoryResult.Success -> {
                val cardDomainList = getFavoriteCardsRepositoryResult.cardDomainList
                val cardViewList = cardDomainList.map { cardDomain: CardDomain ->
                    cardDomain.convertToCardView()
                }
                GetFavoriteCardsUseCaseResult.Success(cardViewList)
            }
            is GetFavoriteCardsRepositoryResult.ErrorUnknown -> {
                GetFavoriteCardsUseCaseResult.ErrorUnknown
            }
        }
    }

    override suspend fun getCategories(email: String): GetCategoriesUseCaseResult {
        when (val getAllCardsRepositoryResult = cardRepository.getAllCards(email)) {
            is GetAllCardsRepositoryResult.Success -> {
                val cardDomainList = getAllCardsRepositoryResult.cardDomainList
                val streamingSize = cardDomainList.filter { it.category == STREAMING_TYPE }.size
                val socialMediaSize =
                    cardDomainList.filter { it.category == SOCIAL_MEDIA_TYPE }.size
                val banksSize = cardDomainList.filter { it.category == BANKS_TYPE }.size
                val educationSize = cardDomainList.filter { it.category == EDUCATION_TYPE }.size
                val workSize = cardDomainList.filter { it.category == WORK_TYPE }.size
                val cardsSize = cardDomainList.filter { it.category == CARD_TYPE }.size

                val categoryViewList = mutableListOf<CategoryView>()
                if (streamingSize > 0) {
                    categoryViewList.add(
                        CategoryDomain(
                            typeName = STREAMING_TYPE,
                            size = streamingSize
                        ).convertToCategoryView()
                    )
                }
                if (socialMediaSize > 0) {
                    categoryViewList.add(
                        CategoryDomain(
                            typeName = SOCIAL_MEDIA_TYPE,
                            size = socialMediaSize
                        ).convertToCategoryView()
                    )
                }
                if (banksSize > 0) {
                    categoryViewList.add(
                        CategoryDomain(
                            typeName = BANKS_TYPE,
                            size = banksSize
                        ).convertToCategoryView()
                    )
                }
                if (educationSize > 0) {
                    categoryViewList.add(
                        CategoryDomain(
                            typeName = EDUCATION_TYPE,
                            size = educationSize
                        ).convertToCategoryView()
                    )
                }
                if (workSize > 0) {
                    categoryViewList.add(
                        CategoryDomain(
                            typeName = WORK_TYPE,
                            size = workSize
                        ).convertToCategoryView()
                    )
                }
                if (cardsSize > 0) {
                    categoryViewList.add(
                        CategoryDomain(
                            typeName = CARD_TYPE,
                            size = cardsSize
                        ).convertToCategoryView()
                    )
                }
                return GetCategoriesUseCaseResult.Success(categoryViewList)
            }
            is GetAllCardsRepositoryResult.ErrorUnknown -> {
                return GetCategoriesUseCaseResult.ErrorUnknown
            }
        }
    }
}