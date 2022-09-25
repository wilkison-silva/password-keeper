package br.com.passwordkeeper.domain.usecases.form_validation_create_card

import android.content.Context
import br.com.passwordkeeper.R
import br.com.passwordkeeper.domain.result.usecase.FormValidationCardUseCaseResult

class FormValidationCreateCardUseCaseImpl(private val context: Context) : FormValidationCreateCardUseCase {

    override operator fun invoke(
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