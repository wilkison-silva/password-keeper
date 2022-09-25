package br.com.passwordkeeper.presentation.features.sign_in

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.passwordkeeper.domain.result.usecase.FormValidationSignInUseCaseResult
import br.com.passwordkeeper.domain.result.usecase.SignInUseCaseResult
import br.com.passwordkeeper.domain.result.viewmodelstate.FormValidationSignInStateResult
import br.com.passwordkeeper.domain.result.viewmodelstate.SignInStateResult
import br.com.passwordkeeper.domain.usecases.form_validation_sign_in.FormValidationSignInUseCase
import br.com.passwordkeeper.domain.usecases.SignInUseCase
import kotlinx.coroutines.launch

class SignInViewModel(
    private val signInUseCase: SignInUseCase,
    private val formValidationSignInUseCase: FormValidationSignInUseCase
) : ViewModel() {

    private val _signInState = MutableLiveData<SignInStateResult>()
    val signInState: LiveData<SignInStateResult>
        get() = _signInState

    private val _formValidationState = MutableLiveData<FormValidationSignInStateResult>()
    val formValidationState: LiveData<FormValidationSignInStateResult>
        get() = _formValidationState

    fun updateFormValidationState(email: String, password: String) {
        when (formValidationSignInUseCase(email, password)) {
            is FormValidationSignInUseCaseResult.ErrorEmailIsBlank ->
                _formValidationState.postValue(FormValidationSignInStateResult.ErrorEmailIsBlank)
            is FormValidationSignInUseCaseResult.ErrorEmailMalFormed ->
                _formValidationState.postValue(FormValidationSignInStateResult.ErrorEmailMalFormed)
            is FormValidationSignInUseCaseResult.ErrorPasswordIsBlank ->
                _formValidationState.postValue(FormValidationSignInStateResult.ErrorPasswordIsBlank)
            is FormValidationSignInUseCaseResult.Success ->
                _formValidationState.postValue(FormValidationSignInStateResult.Success(email, password))
        }
    }

    fun updateSignInState(email: String, password: String) {
        viewModelScope.launch {
            _signInState.postValue(SignInStateResult.Loading)
            when (val signInUseCaseResult =
                signInUseCase.signIn(email, password)) {
                is SignInUseCaseResult.Success -> {
                    _signInState.postValue(SignInStateResult.Success(signInUseCaseResult.userView))
                }
                is SignInUseCaseResult.ErrorEmailOrPasswordWrong -> {
                    _signInState.postValue(SignInStateResult.ErrorEmailOrPasswordWrong)
                }
                is SignInUseCaseResult.ErrorUnknown -> {
                    _signInState.postValue(SignInStateResult.ErrorUnknown)
                }
            }
        }
    }

    fun updateStatesToEmptyState() {
        _signInState.postValue(SignInStateResult.EmptyState)
        _formValidationState.postValue(FormValidationSignInStateResult.EmptyState)
    }

}

