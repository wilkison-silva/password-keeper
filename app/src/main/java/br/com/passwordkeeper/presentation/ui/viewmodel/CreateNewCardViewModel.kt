package br.com.passwordkeeper.presentation.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.passwordkeeper.domain.result.usecase.CreateCardUseCaseResult
import br.com.passwordkeeper.domain.result.usecase.FormValidationCardUseCaseResult
import br.com.passwordkeeper.domain.result.usecase.GetCurrentUserUseCaseResult
import br.com.passwordkeeper.domain.result.viewmodelstate.CreateCardStateResult
import br.com.passwordkeeper.domain.result.viewmodelstate.FormValidationCardStateResult
import br.com.passwordkeeper.domain.usecase.CardUseCase
import br.com.passwordkeeper.domain.usecase.FormValidationCardUseCase
import br.com.passwordkeeper.domain.usecase.SignInUseCase
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class CreateNewCardViewModel(
    private val cardUseCase: CardUseCase,
    private val formValidationCardUseCase: FormValidationCardUseCase,
    private val signInUseCase: SignInUseCase,
) : ViewModel() {

    private val _favorite = MutableLiveData<Boolean>(false)
    val favorite: LiveData<Boolean>
        get() = _favorite

    private val _emailUser = MutableLiveData<String?>()
    val emailUser: LiveData<String?>
        get() = _emailUser

    private val _formValidationCard = MutableLiveData<FormValidationCardStateResult>()
    val formValidationCard: LiveData<FormValidationCardStateResult>
        get() = _formValidationCard

    private val _createCardState = MutableLiveData<CreateCardStateResult>()
    val createCardState: LiveData<CreateCardStateResult>
        get() = _createCardState


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
        val resultFormValidation = formValidationCardUseCase.validateForm(
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
                _formValidationCard.postValue(FormValidationCardStateResult.Success)
            }
        }
    }

    fun getCurrentEmailUser() {
        viewModelScope.launch {
            when (val resultUser = signInUseCase.getCurrentUser()) {
                is GetCurrentUserUseCaseResult.ErrorUnknown -> {
                    _emailUser.postValue(null)
                }
                is GetCurrentUserUseCaseResult.Success -> {
                    _emailUser.postValue(resultUser.userView.email)
                }
            }

        }
    }

    fun createCard(
        description: String,
        login: String,
        password: String,
        category: String,
        isFavorite: Boolean,
        date: String,
        emailUser: String
    ) {
        viewModelScope.launch {
            val resultCreateCard = cardUseCase.createCard(
                description = description,
                login = login,
                password = password,
                category = category,
                isFavorite = isFavorite,
                date = date,
                emailUser = emailUser
            )
            when(resultCreateCard) {
                is CreateCardUseCaseResult.ErrorUnknown -> {
                    _createCardState.postValue(CreateCardStateResult.ErrorUnknown)
                }
                is CreateCardUseCaseResult.Success -> {
                    _createCardState.postValue(CreateCardStateResult.Success(resultCreateCard.cardId))
                }
            }
        }
    }

    fun updateFavorite() {
        _favorite.value?.let {
            _favorite.postValue(!it)
        }
    }
}