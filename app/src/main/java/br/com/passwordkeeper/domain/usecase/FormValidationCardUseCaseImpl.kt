package br.com.passwordkeeper.domain.usecase

import br.com.passwordkeeper.domain.model.Categories
import br.com.passwordkeeper.domain.result.usecase.FormValidationCardUseCaseResult

class FormValidationCardUseCaseImpl() : FormValidationCardUseCase {

    override fun validateForm(
        description: String,
        login: String,
        password: String,
        category: String,
        isFavorite: Boolean,
        date: String
    ): FormValidationCardUseCaseResult {
        if (description.isBlank())
            return FormValidationCardUseCaseResult.DescriptionIsEmpty
        if (!validateCategory(category))
            return FormValidationCardUseCaseResult.CategoryNotSelected
        return FormValidationCardUseCaseResult.Success
    }

    private fun validateCategory(category: String): Boolean {
        return try {
            Categories.valueOf(category)
            true
        } catch (e: Exception) {
            false
        }
    }

}