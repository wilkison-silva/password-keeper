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
                val categoriesList = getAllCardsRepositoryResult.cardDomainList.map { it.category }
                if (categoriesList.isNotEmpty()) {
                    val categoriesViewList = mutableListOf<CategoryView>()

                    addCategoryView(categoriesList, categoriesViewList, STREAMING_TYPE)
                    addCategoryView(categoriesList, categoriesViewList, SOCIAL_MEDIA_TYPE)
                    addCategoryView(categoriesList, categoriesViewList, BANKS_TYPE)
                    addCategoryView(categoriesList, categoriesViewList, EDUCATION_TYPE)
                    addCategoryView(categoriesList, categoriesViewList, WORK_TYPE)
                    addCategoryView(categoriesList, categoriesViewList, CARD_TYPE)

                    return GetCategoriesUseCaseResult.SuccessWithElements(categoriesViewList)
                }
                return GetCategoriesUseCaseResult.SuccessWithoutElements
            }
            is GetAllCardsRepositoryResult.ErrorUnknown ->
                return GetCategoriesUseCaseResult.ErrorUnknown
        }
    }

    private fun addCategoryView(
        categoriesList: List<String>,
        categoriesViewList: MutableList<CategoryView>,
        typeName: String,
    ) {
        categoriesList.filter { it == typeName }.size.let { size ->
            if (size > 0) {
                categoriesViewList.add(
                    CategoryDomain(
                        typeName = typeName,
                        size = size
                    ).convertToCategoryView()
                )
            }
        }
    }

}