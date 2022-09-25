package br.com.passwordkeeper.presentation.features.sign_up

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.passwordkeeper.domain.usecases.create_user.CreateUserUseCaseResult
import br.com.passwordkeeper.domain.usecases.password_validation.ErrorsValidationPassword
import br.com.passwordkeeper.domain.usecases.password_validation.ErrorsValidationPassword.*
import br.com.passwordkeeper.domain.usecases.form_validation_sign_up.FormValidationSignUpUseCaseResult
import br.com.passwordkeeper.domain.usecases.password_validation.PasswordValidationUseCaseResult
import br.com.passwordkeeper.presentation.features.sign_up.states.FormValidationSignUpState.*
import br.com.passwordkeeper.domain.usecases.create_user.CreateUserUseCase
import br.com.passwordkeeper.domain.usecases.form_validation_sign_up.FormValidationSignUpUseCase
import br.com.passwordkeeper.domain.usecases.password_validation.PasswordValidationUseCase
import br.com.passwordkeeper.presentation.features.sign_up.states.CreateUserState
import br.com.passwordkeeper.presentation.features.sign_up.states.FormValidationSignUpState
import br.com.passwordkeeper.presentation.features.sign_up.states.ValidationState
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val createUserUseCase: CreateUserUseCase,
    private val formValidationSignUpUseCase: FormValidationSignUpUseCase,
    private val passwordValidationUseCase: PasswordValidationUseCase,
) : ViewModel() {

    private val _createUserState = MutableLiveData<CreateUserState>()
    val createUserState: LiveData<CreateUserState>
        get() = _createUserState

    private val _formValidationState = MutableLiveData<FormValidationSignUpState>()
    val formValidationState: LiveData<FormValidationSignUpState>
        get() = _formValidationState

    private val _passwordFieldIsEmptyState = MutableLiveData<Unit>()
    val passwordFieldIsEmptyState: LiveData<Unit>
        get() = _passwordFieldIsEmptyState


    private val _passwordUpperLetterState = MutableLiveData<ValidationState>()
    val passwordUpperLetterState: LiveData<ValidationState>
        get() = _passwordUpperLetterState

    private val _passwordLowerLetterState = MutableLiveData<ValidationState>()
    val passwordLowerLetterState: LiveData<ValidationState>
        get() = _passwordLowerLetterState

    private val _passwordSpecialLetterState = MutableLiveData<ValidationState>()
    val passwordSpecialCharacterState: LiveData<ValidationState>
        get() = _passwordSpecialLetterState

    private val _passwordNumericLetterState = MutableLiveData<ValidationState>()
    val passwordNumericCharactersState: LiveData<ValidationState>
        get() = _passwordNumericLetterState

    private val _passwordLengthState = MutableLiveData<ValidationState>()
    val passwordLengthState: LiveData<ValidationState>
        get() = _passwordLengthState

    fun updateFormValidationState(
        name: String,
        email: String,
        password: String,
        confirmedPassword: String,
    ) {
        when (formValidationSignUpUseCase(name, email, password, confirmedPassword)) {
            is FormValidationSignUpUseCaseResult.ErrorEmailIsBlank ->
                _formValidationState.postValue(ErrorEmailIsBlank)
            is FormValidationSignUpUseCaseResult.ErrorEmailMalFormed ->
                _formValidationState.postValue(ErrorEmailMalFormed)
            is FormValidationSignUpUseCaseResult.ErrorNameIsBlank ->
                _formValidationState.postValue(ErrorNameIsBlank)
            is FormValidationSignUpUseCaseResult.ErrorPasswordIsBlank ->
                _formValidationState.postValue(ErrorPasswordIsBlank)
            is FormValidationSignUpUseCaseResult.ErrorPasswordTooWeak ->
                _formValidationState.postValue(ErrorPasswordTooWeak)
            is FormValidationSignUpUseCaseResult.ErrorPasswordsDoNotMatch ->
                _formValidationState.postValue(ErrorPasswordsDoNotMatch)
            is FormValidationSignUpUseCaseResult.Success ->
                _formValidationState.postValue(Success(name, email, password))
        }

    }

    fun updateSignUpState(name: String, email: String, password: String) {
        viewModelScope.launch {
            when (createUserUseCase(name, email, password)) {
                CreateUserUseCaseResult.ErrorEmailAlreadyExists ->
                    _createUserState.postValue(CreateUserState.ErrorEmailAlreadyExists)
                CreateUserUseCaseResult.ErrorEmailMalformed ->
                    _createUserState.postValue(CreateUserState.ErrorEmailMalformed)
                CreateUserUseCaseResult.ErrorUnknown ->
                    _createUserState.postValue(CreateUserState.ErrorUnknown)
                CreateUserUseCaseResult.ErrorWeakPassword ->
                    _createUserState.postValue(CreateUserState.ErrorWeakPassword)
                CreateUserUseCaseResult.Success ->
                    _createUserState.postValue(CreateUserState.Success)
            }
        }
    }

    fun updateStatesToEmptyState() {
        _createUserState.postValue(CreateUserState.EmptyState)
        _formValidationState.postValue(EmptyState)
    }

    private fun setPasswordState(
        errorList: List<ErrorsValidationPassword>,
        error: ErrorsValidationPassword,
        state: MutableLiveData<ValidationState>,
    ) {
        errorList.contains(error).let {
            if (it)
                state.postValue(ValidationState.Error)
            else
                state.postValue(ValidationState.Success)
        }
    }

    fun updatePasswordValidation(password: String) {
        when (val passwordValidationUseCaseResult =
            passwordValidationUseCase(password)) {
            is PasswordValidationUseCaseResult.ErrorsFound -> {
                passwordValidationUseCaseResult.errorList.let {
                    setPasswordState(it, ErrorOneUpperLetterNotFound, _passwordUpperLetterState)
                    setPasswordState(it, ErrorOneLowerLetterNotFound, _passwordLowerLetterState)
                    setPasswordState(it, ErrorOneSpecialLetterNotFound, _passwordSpecialLetterState)
                    setPasswordState(it, ErrorOneNumericLetterNotFound, _passwordNumericLetterState)
                    setPasswordState(it, ErrorPasswordSizeNotMatch, _passwordLengthState)
                }
            }

            is PasswordValidationUseCaseResult.PasswordFieldEmpty ->
                _passwordFieldIsEmptyState.postValue(Unit)
        }
    }
}
