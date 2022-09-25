package br.com.passwordkeeper.presentation.features.sign_in

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.passwordkeeper.domain.usecases.form_validation_sign_in.FormValidationSignInUseCaseResult
import br.com.passwordkeeper.domain.usecases.sign_in.SignInUseCaseResult
import br.com.passwordkeeper.domain.usecases.form_validation_sign_in.FormValidationSignInUseCase
import br.com.passwordkeeper.domain.usecases.sign_in.SignInUseCase
import br.com.passwordkeeper.presentation.features.sign_in.states.FormValidationSignInState
import br.com.passwordkeeper.presentation.features.sign_in.states.SignInState
import kotlinx.coroutines.launch

class SignInViewModel(
    private val signInUseCase: SignInUseCase,
    private val formValidationSignInUseCase: FormValidationSignInUseCase
) : ViewModel() {

    private val _signInState = MutableLiveData<SignInState>()
    val signInState: LiveData<SignInState>
        get() = _signInState

    private val _formValidationState = MutableLiveData<FormValidationSignInState>()
    val formValidationState: LiveData<FormValidationSignInState>
        get() = _formValidationState

    fun updateFormValidationState(email: String, password: String) {
        when (formValidationSignInUseCase(email, password)) {
            is FormValidationSignInUseCaseResult.ErrorEmailIsBlank ->
                _formValidationState.postValue(FormValidationSignInState.ErrorEmailIsBlank)
            is FormValidationSignInUseCaseResult.ErrorEmailMalFormed ->
                _formValidationState.postValue(FormValidationSignInState.ErrorEmailMalFormed)
            is FormValidationSignInUseCaseResult.ErrorPasswordIsBlank ->
                _formValidationState.postValue(FormValidationSignInState.ErrorPasswordIsBlank)
            is FormValidationSignInUseCaseResult.Success ->
                _formValidationState.postValue(FormValidationSignInState.Success(email, password))
        }
    }

    fun updateSignInState(email: String, password: String) {
        viewModelScope.launch {
            _signInState.postValue(SignInState.Loading)
            when (val signInUseCaseResult =
                signInUseCase(email, password)) {
                is SignInUseCaseResult.Success -> {
                    _signInState.postValue(SignInState.Success(signInUseCaseResult.userView))
                }
                is SignInUseCaseResult.ErrorEmailOrPasswordWrong -> {
                    _signInState.postValue(SignInState.ErrorEmailOrPasswordWrong)
                }
                is SignInUseCaseResult.ErrorUnknown -> {
                    _signInState.postValue(SignInState.ErrorUnknown)
                }
            }
        }
    }

    fun updateStatesToEmptyState() {
        _signInState.postValue(SignInState.EmptyState)
        _formValidationState.postValue(FormValidationSignInState.EmptyState)
    }

}

