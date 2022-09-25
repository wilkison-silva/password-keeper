package br.com.passwordkeeper.presentation.features.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.passwordkeeper.domain.usecases.form_validation_sign_in.FormValidationSignInUseCase
import br.com.passwordkeeper.domain.usecases.form_validation_sign_in.FormValidationSignInUseCaseResult
import br.com.passwordkeeper.domain.usecases.sign_in.SignInUseCase
import br.com.passwordkeeper.domain.usecases.sign_in.SignInUseCaseResult
import br.com.passwordkeeper.presentation.features.sign_in.states.FormValidationSignInState
import br.com.passwordkeeper.presentation.features.sign_in.states.SignInState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignInViewModel(
    private val signInUseCase: SignInUseCase,
    private val formValidationSignInUseCase: FormValidationSignInUseCase
) : ViewModel() {

    private val _signInState = MutableStateFlow<SignInState>(SignInState.EmptyState)
    val signInState = _signInState.asStateFlow()

    private val _formValidationState = MutableStateFlow<FormValidationSignInState>(FormValidationSignInState.EmptyState)
    val formValidationState = _formValidationState

    fun updateFormValidationState(email: String, password: String) {
        when (formValidationSignInUseCase(email, password)) {
            is FormValidationSignInUseCaseResult.ErrorEmailIsBlank ->
                _formValidationState.value = FormValidationSignInState.ErrorEmailIsBlank
            is FormValidationSignInUseCaseResult.ErrorEmailMalFormed ->
                _formValidationState.value = FormValidationSignInState.ErrorEmailMalFormed
            is FormValidationSignInUseCaseResult.ErrorPasswordIsBlank ->
                _formValidationState.value = FormValidationSignInState.ErrorPasswordIsBlank
            is FormValidationSignInUseCaseResult.Success ->
                _formValidationState.value = FormValidationSignInState.Success(email, password)
        }
    }

    fun updateSignInState(email: String, password: String) {
        viewModelScope.launch {
            _signInState.value = SignInState.Loading
            when (val signInUseCaseResult =
                signInUseCase(email, password)) {
                is SignInUseCaseResult.Success -> {
                    _signInState.value = SignInState.Success(signInUseCaseResult.userView)
                }
                is SignInUseCaseResult.ErrorEmailOrPasswordWrong -> {
                    _signInState.value = SignInState.ErrorEmailOrPasswordWrong
                }
                is SignInUseCaseResult.ErrorUnknown -> {
                    _signInState.value = SignInState.ErrorUnknown
                }
            }
        }
    }

    fun updateStatesToEmptyState() {
        _signInState.value = SignInState.EmptyState
        _formValidationState.value = FormValidationSignInState.EmptyState
    }

}

