package br.com.passwordkeeper.domain.usecases.get_items_count_by_categories

import br.com.passwordkeeper.commons.Categories
import br.com.passwordkeeper.domain.mapper.CardDomainMapper
import br.com.passwordkeeper.domain.mapper.CategoryDomainMapper
import br.com.passwordkeeper.domain.model.CategoryDomain
import br.com.passwordkeeper.domain.repository.CardRepository
import br.com.passwordkeeper.domain.result.repository.GetAllCardsRepositoryResult
import br.com.passwordkeeper.domain.result.usecase.GetCategoriesSizeUseCaseResult
import br.com.passwordkeeper.presentation.model.CategoryView

class GetItemsCountByCategoriesUseCaseImpl(
    private val cardRepository: CardRepository,
    private val categoryDomainMapper: CategoryDomainMapper
) : GetItemsCountByCategoriesUseCase {

    override suspend operator fun invoke(email: String): GetCategoriesSizeUseCaseResult {
        when (val getAllCardsRepositoryResult = cardRepository.getAllCards(email)) {
            is GetAllCardsRepositoryResult.Success -> {
                val categoriesList: MutableList<Categories> =
                    getAllCardsRepositoryResult.cardDomainList.map { cardDomain ->
                        when (cardDomain.category) {
                            Categories.STREAMING.name -> Categories.STREAMING
                            Categories.SOCIAL_MEDIA.name-> Categories.SOCIAL_MEDIA
                            Categories.BANKS.name -> Categories.BANKS
                            Categories.EDUCATION.name -> Categories.EDUCATION
                            Categories.WORK.name -> Categories.WORK
                            else -> Categories.CARD
                        }
                    }.toMutableList()
                val categoriesViewList = mutableListOf<CategoryView>()

                addCategoryView(categoriesList, categoriesViewList, Categories.STREAMING)
                addCategoryView(categoriesList, categoriesViewList, Categories.SOCIAL_MEDIA)
                addCategoryView(categoriesList, categoriesViewList, Categories.BANKS)
                addCategoryView(categoriesList, categoriesViewList, Categories.EDUCATION)
                addCategoryView(categoriesList, categoriesViewList, Categories.WORK)
                addCategoryView(categoriesList, categoriesViewList, Categories.CARD)

                return GetCategoriesSizeUseCaseResult.Success(categoriesViewList)

            }
            is GetAllCardsRepositoryResult.ErrorUnknown ->
                return GetCategoriesSizeUseCaseResult.ErrorUnknown
        }
    }

    private fun addCategoryView(
        categoriesList: MutableList<Categories>,
        categoriesViewList: MutableList<CategoryView>,
        category: Categories,
    ) {
        categoriesList.filter { it == category }.size.let { size ->
            if (size > 0) {
                val categoryView = categoryDomainMapper.transform(
                    CategoryDomain(
                        category = category,
                        quantity = size
                    )
                )
                categoriesViewList.add(categoryView)
            }
        }
        categoriesList.removeAll { it == category }
    }

}