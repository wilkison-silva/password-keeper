package br.com.passwordkeeper.presentation.features.create_card

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.passwordkeeper.commons.Categories
import br.com.passwordkeeper.domain.result.usecase.CreateCardUseCaseResult
import br.com.passwordkeeper.domain.result.usecase.FormValidationCardUseCaseResult
import br.com.passwordkeeper.domain.result.viewmodelstate.CreateCardStateResult
import br.com.passwordkeeper.domain.result.viewmodelstate.FormValidationCardStateResult
import br.com.passwordkeeper.domain.usecases.create_card.CreateCardUseCase
import br.com.passwordkeeper.domain.usecases.form_validation_create_card.FormValidationCreateCardUseCase
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class CreateNewCardViewModel(
    private val createCardUseCase: CreateCardUseCase,
    private val formValidationCreateCardUseCase: FormValidationCreateCardUseCase
) : ViewModel() {

    private val _favorite = MutableLiveData(false)
    val favorite: LiveData<Boolean>
        get() = _favorite

    private val _formValidationCard = MutableLiveData<FormValidationCardStateResult>()
    val formValidationCard: LiveData<FormValidationCardStateResult>
        get() = _formValidationCard

    private val _createCardState = MutableLiveData<CreateCardStateResult>()
    val createCardState: LiveData<CreateCardStateResult>
        get() = _createCardState

    private val _categorySelected = MutableLiveData<Categories>()
    val categorySelected: LiveData<Categories>
        get() = _categorySelected


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
            FormValidationCardUseCaseResult.CategoryNotSelected -> {
                _formValidationCard.postValue(FormValidationCardStateResult.CategoryNotSelected)
            }
            FormValidationCardUseCaseResult.DescriptionIsEmpty -> {
                _formValidationCard.postValue(FormValidationCardStateResult.DescriptionIsEmpty)
            }
            FormValidationCardUseCaseResult.Success -> {
                val formValidationSuccess = FormValidationCardStateResult.Success(
                    description = description,
                    login = login,
                    password = password,
                    category = category,
                    isFavorite = isFavorite,
                    date = date
                )
                _formValidationCard.postValue(formValidationSuccess)
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
            _createCardState.postValue(CreateCardStateResult.Loading)
            _categorySelected.value?.let { category ->
                val resultCreateCard = createCardUseCase(
                    description = description,
                    login = login,
                    password = password,
                    category = category,
                    isFavorite = isFavorite,
                    date = date,
                    emailUser = emailUser
                )
                when (resultCreateCard) {
                    is CreateCardUseCaseResult.ErrorUnknown -> {
                        _createCardState.postValue(CreateCardStateResult.ErrorUnknown)
                    }
                    is CreateCardUseCaseResult.Success -> {
                        _createCardState.postValue(CreateCardStateResult.Success(resultCreateCard.cardId))
                    }
                }
            } ?: _createCardState.postValue(CreateCardStateResult.ErrorUnknown)
        }
    }

    fun updateFavorite() {
        _favorite.value?.let {
            _favorite.postValue(!it)
        }
    }

    fun updateCategorySelected(category: Categories) {
        _categorySelected.postValue(category)
    }
}