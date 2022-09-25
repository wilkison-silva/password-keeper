package br.com.passwordkeeper.presentation.features.sign_up

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.passwordkeeper.domain.result.usecase.*
import br.com.passwordkeeper.domain.result.usecase.ErrorsValidationPassword.*
import br.com.passwordkeeper.domain.result.viewmodelstate.*
import br.com.passwordkeeper.domain.result.viewmodelstate.FormValidationSignUpStateResult.*
import br.com.passwordkeeper.domain.usecases.form_validation_sign_up.FormValidationSignUpUseCase
import br.com.passwordkeeper.domain.usecases.password_validation.PasswordValidationUseCase
import br.com.passwordkeeper.domain.usecases.SignUpUseCase
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val signUpUseCase: SignUpUseCase,
    private val formValidationSignUpUseCase: FormValidationSignUpUseCase,
    private val passwordValidationUseCase: PasswordValidationUseCase,
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

    private val _passwordSpecialLetterState = MutableLiveData<ValidationStateResult>()
    val passwordSpecialCharacterState: LiveData<ValidationStateResult>
        get() = _passwordSpecialLetterState

    private val _passwordNumericLetterState = MutableLiveData<ValidationStateResult>()
    val passwordNumericCharactersState: LiveData<ValidationStateResult>
        get() = _passwordNumericLetterState

    private val _passwordLengthState = MutableLiveData<ValidationStateResult>()
    val passwordLengthState: LiveData<ValidationStateResult>
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
        _formValidationState.postValue(EmptyState)
    }

    private fun setPasswordState(
        errorList: List<ErrorsValidationPassword>,
        error: ErrorsValidationPassword,
        state: MutableLiveData<ValidationStateResult>,
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
