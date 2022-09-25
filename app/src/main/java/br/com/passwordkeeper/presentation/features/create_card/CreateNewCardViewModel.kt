package br.com.passwordkeeper.presentation.features.create_card

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.passwordkeeper.commons.Categories
import br.com.passwordkeeper.domain.usecases.create_card.CreateCardUseCaseResult
import br.com.passwordkeeper.domain.usecases.form_validation_create_card.FormValidationCreateCardUseCaseResult
import br.com.passwordkeeper.domain.usecases.create_card.CreateCardUseCase
import br.com.passwordkeeper.domain.usecases.form_validation_create_card.FormValidationCreateCardUseCase
import br.com.passwordkeeper.presentation.features.create_card.states.CreateCardState
import br.com.passwordkeeper.presentation.features.create_card.states.FormValidationCardState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class CreateNewCardViewModel(
    private val createCardUseCase: CreateCardUseCase,
    private val formValidationCreateCardUseCase: FormValidationCreateCardUseCase
) : ViewModel() {

    private val _favoriteState = MutableStateFlow(false)
    val favoriteState = _favoriteState.asStateFlow()

    private val _formValidationState =
        MutableStateFlow<FormValidationCardState>(FormValidationCardState.EmptyState)
    val formValidationState = _formValidationState.asStateFlow()

    private val _createCardState = MutableStateFlow<CreateCardState>(CreateCardState.EmptyState)
    val createCardState = _createCardState.asStateFlow()

    private val _categorySelectedState = MutableStateFlow(Categories.NONE)
    val categorySelectedState = _categorySelectedState.asStateFlow()


    fun getCurrentDateTime(): String {
        val date = Calendar.getInstance().time
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        return sdf.format(date)
    }

    fun validateForm(
        description: String,
        login: String,
        password: String,
        category: String,
        isFavorite: Boolean,
        date: String
    ) {
        val resultFormValidation = formValidationCreateCardUseCase(
            description,
            login,
            password,
            category,
            isFavorite,
            date
        )
        when (resultFormValidation) {
            FormValidationCreateCardUseCaseResult.CategoryNotSelected -> {
                _formValidationState.value = FormValidationCardState.CategoryNotSelected
            }
            FormValidationCreateCardUseCaseResult.DescriptionIsEmpty -> {
                _formValidationState.value = FormValidationCardState.DescriptionIsEmpty
            }
            FormValidationCreateCardUseCaseResult.Success -> {
                val formValidationSuccess = FormValidationCardState.Success(
                    description = description,
                    login = login,
                    password = password,
                    category = category,
                    isFavorite = isFavorite,
                    date = date
                )
                _formValidationState.value = formValidationSuccess
            }
        }
    }

    fun createCard(
        description: String,
        login: String,
        password: String,
        isFavorite: Boolean,
        date: String,
        emailUser: String
    ) {
        viewModelScope.launch {
            _createCardState.value = CreateCardState.Loading
            val resultCreateCard = createCardUseCase(
                description = description,
                login = login,
                password = password,
                category = _categorySelectedState.value,
                isFavorite = isFavorite,
                date = date,
                emailUser = emailUser
            )
            when (resultCreateCard) {
                is CreateCardUseCaseResult.ErrorUnknown -> {
                    _createCardState.value = CreateCardState.ErrorUnknown
                }
                is CreateCardUseCaseResult.Success -> {
                    _createCardState.value = CreateCardState.Success(resultCreateCard.cardId)
                }
            }
        }
    }

    fun updateFavorite() {
        _favoriteState.value = !_favoriteState.value
    }

    fun updateCategorySelected(category: Categories) {
        _categorySelectedState.value = category
    }
}