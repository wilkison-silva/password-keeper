package br.com.passwordkeeper.domain.usecase

import android.content.Context
import br.com.passwordkeeper.R
import br.com.passwordkeeper.domain.model.Categories
import br.com.passwordkeeper.domain.result.usecase.FormValidationCardUseCaseResult

class FormValidationCardUseCaseImpl(private val context: Context) : FormValidationCardUseCase {

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
       return category != context.getString(R.string.category)
    }

}