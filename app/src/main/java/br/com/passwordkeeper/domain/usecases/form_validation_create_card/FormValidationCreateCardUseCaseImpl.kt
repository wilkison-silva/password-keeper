package br.com.passwordkeeper.domain.usecases.form_validation_create_card

import android.content.Context
import br.com.passwordkeeper.R

class FormValidationCreateCardUseCaseImpl(private val context: Context) : FormValidationCreateCardUseCase {

    override operator fun invoke(
        description: String,
        login: String,
        password: String,
        category: String,
        isFavorite: Boolean,
        date: String
    ): FormValidationCreateCardUseCaseResult {
        if (description.isBlank())
            return FormValidationCreateCardUseCaseResult.DescriptionIsEmpty
        if (!validateCategory(category))
            return FormValidationCreateCardUseCaseResult.CategoryNotSelected
        return FormValidationCreateCardUseCaseResult.Success
    }

    private fun validateCategory(category: String): Boolean {
       return category != context.getString(R.string.category)
    }

}