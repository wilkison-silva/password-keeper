package br.com.passwordkeeper.presentation.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.passwordkeeper.domain.result.usecase.*
import br.com.passwordkeeper.domain.result.usecase.ErrorsValidationPassword.*
import br.com.passwordkeeper.domain.result.usecase.PasswordValidationUseCaseResult.*
import br.com.passwordkeeper.domain.result.viewmodelstate.*
import br.com.passwordkeeper.domain.usecase.FormValidationSignUpUseCase
import br.com.passwordkeeper.domain.usecase.PasswordValidationUseCase
import br.com.passwordkeeper.domain.usecase.SignUpUseCase
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val signUpUseCase: SignUpUseCase,
    private val formValidationSignUpUseCase: FormValidationSignUpUseCase,
    private val passwordValidationUseCase: PasswordValidationUseCase
) : ViewModel() {

    private val _createUserState = MutableLiveData<CreateUserStateResult>()
    val createUserState: LiveData<CreateUserStateResult>
        get() = _createUserState

    private val _formValidationState = MutableLiveData<FormValidationSignUpStateResult>()
    val formValidationState: LiveData<FormValidationSignUpStateResult>
        get() = _formValidationState

    private val _passwordFieldIsEmptyState = MutableLiveData<Unit>()
    val passwordFieldIsEmptyState: LiveData<Unit>
        get() = _passwordFieldIsEmptyState


    private val _passwordUpperLetterState = MutableLiveData<ValidationStateResult>()
    val passwordUpperLetterState: LiveData<ValidationStateResult>
        get() = _passwordUpperLetterState

    private val _passwordLowerLetterState = MutableLiveData<ValidationStateResult>()
    val passwordLowerLetterState: LiveData<ValidationStateResult>
        get() = _passwordLowerLetterState

    private val _passwordSpecialCharacterState = MutableLiveData<ValidationStateResult>()
    val passwordSpecialCharacterState: LiveData<ValidationStateResult>
        get() = _passwordSpecialCharacterState

    private val _passwordNumericCharacterState = MutableLiveData<ValidationStateResult>()
    val passwordNumericCharactersState: LiveData<ValidationStateResult>
        get() = _passwordNumericCharacterState

    private val _passwordLengthState = MutableLiveData<ValidationStateResult>()
    val passwordLengthState: LiveData<ValidationStateResult>
        get() = _passwordLengthState

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
                _formValidationState.postValue(
                    FormValidationSignUpStateResult.Success(
                        name,
                        email,
                        password
                    )
                )
        }

    }

    fun updateSignUpState(name: String, email: String, password: String) {
        viewModelScope.launch {
            when (signUpUseCase.createUser(name, email, password)) {
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

    fun updateStatesToEmptyState() {
        _createUserState.postValue(CreateUserStateResult.EmptyState)
        _formValidationState.postValue(FormValidationSignUpStateResult.EmptyState)
    }

    private fun setPasswordState(
        errorList: List<ErrorsValidationPassword>,
        error: ErrorsValidationPassword,
        state: MutableLiveData<ValidationStateResult>
    ) {
        errorList.contains(error).let {
            if (it)
                state.postValue(ValidationStateResult.Error)
            else
                state.postValue(ValidationStateResult.Success)
        }
    }

    fun updatePasswordValidation(password: String) {
        when (val passwordValidationUseCaseResult =
            passwordValidationUseCase.validatePassword(password)) {
            is ErrorsFound -> {
                passwordValidationUseCaseResult.errorList.let {
                    setPasswordState(it, ErrorOneUpperLetterNotFound, _passwordUpperLetterState)
                    setPasswordState(it, ErrorOneLowerLetterNotFound, _passwordLowerLetterState)
                    setPasswordState(
                        it,
                        ErrorOneSpecialCharacterNotFound,
                        _passwordSpecialCharacterState
                    )
                    setPasswordState(
                        it,
                        ErrorOneNumericCharacterNotFound,
                        _passwordNumericCharacterState
                    )
                    setPasswordState(it, ErrorPasswordLengthNotMatch, _passwordLengthState)
                }
            }

            is PasswordFieldEmpty ->
                _passwordFieldIsEmptyState.postValue(Unit)
        }
    }
}
