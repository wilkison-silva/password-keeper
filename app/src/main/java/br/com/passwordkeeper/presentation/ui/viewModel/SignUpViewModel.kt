package br.com.passwordkeeper.presentation.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.passwordkeeper.domain.result.usecase.*
import br.com.passwordkeeper.domain.result.viewmodelstate.*
import br.com.passwordkeeper.domain.usecase.FormValidationSignUpUseCase
import br.com.passwordkeeper.domain.usecase.SignUpUseCase
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val signUpUseCase: SignUpUseCase,
    private val formValidationSignUpUseCase: FormValidationSignUpUseCase
) : ViewModel() {

//    private val _signUpState = MutableLiveData<SignUpStateResult>()
//    val signUpState: LiveData<SignUpStateResult>
//        get() = _signUpState

    private val _createUserState = MutableLiveData<CreateUserStateResult>()
    val createUserState: LiveData<CreateUserStateResult>
        get() = _createUserState

    private val _formValidationState = MutableLiveData<FormValidationSignUpStateResult>()
    val formValidationState: LiveData<FormValidationSignUpStateResult>
        get() = _formValidationState

    fun updateFormValidationState(
        name: String,
        email: String,
        password: String,
        confirmedPassword: String
    ) {
        val formValidationSignUpUseCaseResult =
            formValidationSignUpUseCase.validateForm(name, email, password, confirmedPassword)
        when (formValidationSignUpUseCaseResult) {
            is FormValidationSignUpUseCaseResult.ErrorEmailIsBlank ->
                _formValidationState.postValue(FormValidationSignUpStateResult.ErrorEmailIsBlank)
            is FormValidationSignUpUseCaseResult.ErrorEmailMalFormed ->
                _formValidationState.postValue(FormValidationSignUpStateResult.ErrorEmailMalFormed)
            is FormValidationSignUpUseCaseResult.ErrorNameIsBlank ->
                _formValidationState.postValue(FormValidationSignUpStateResult.ErrorNameIsBlank)
            is FormValidationSignUpUseCaseResult.ErrorPasswordIsBlank ->
                _formValidationState.postValue(FormValidationSignUpStateResult.ErrorPasswordIsBlank)
            is FormValidationSignUpUseCaseResult.ErrorPasswordTooWeak ->
                _formValidationState.postValue(FormValidationSignUpStateResult.ErrorPasswordTooWeak)
            is FormValidationSignUpUseCaseResult.ErrorPasswordsDoNotMatch ->
                _formValidationState.postValue(FormValidationSignUpStateResult.ErrorPasswordsDoNotMatch)
            is FormValidationSignUpUseCaseResult.Success ->
                _formValidationState.postValue(FormValidationSignUpStateResult.Success(name, email, password))
        }

    }

    fun updateSignUpState(name: String, email: String, password: String) {
        viewModelScope.launch {
            when (val signUpUseCaseResult =
                signUpUseCase.createUser(name, email, password)) {
                CreateUserUseCaseResult.ErrorEmailAlreadyExists ->
                    _createUserState.postValue(CreateUserStateResult.ErrorEmailAlreadyExists)
                    CreateUserUseCaseResult.ErrorEmailMalformed ->
                        _createUserState.postValue(CreateUserStateResult.ErrorEmailMalformed)
                CreateUserUseCaseResult.ErrorUnknown ->
                    _createUserState.postValue(CreateUserStateResult.ErrorUnknown)
                CreateUserUseCaseResult.ErrorWeakPassword ->
                    _createUserState.postValue(CreateUserStateResult.ErrorWeakPassword)
                CreateUserUseCaseResult.Success ->
                    _createUserState.postValue(CreateUserStateResult.Success)
            }
        }
    }
}
