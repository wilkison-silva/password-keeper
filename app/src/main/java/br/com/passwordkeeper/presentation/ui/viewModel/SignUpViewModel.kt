package br.com.passwordkeeper.presentation.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.passwordkeeper.domain.result.usecase.*
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

    private val _specialCharacterState = MutableLiveData<ValidationStateResult>()
    val specialCharacterState: LiveData<ValidationStateResult>
        get() = _specialCharacterState

    private val _numericCharacterState = MutableLiveData<ValidationStateResult>()
    val numericCharactersState: LiveData<ValidationStateResult>
        get() = _numericCharacterState

    private val _passwordLengthState= MutableLiveData<ValidationStateResult>()
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

    fun updatePasswordValidation(password: String) {
        val passwordValidationUseCaseResult = passwordValidationUseCase.validatePassword(password)
        when (passwordValidationUseCaseResult) {
            is PasswordValidationUseCaseResult.ErrorsFound -> {
                var listError = passwordValidationUseCaseResult.errorList
                var hasError = false
                for (error in listError) {
                    if (error is ErrorsValidationPassword.ErrorOneUpperLetterNotFound)
                        hasError = true
                }
                if (hasError == true) {
                    _passwordUpperLetterState.postValue(ValidationStateResult.Error)
                } else {
                    _passwordUpperLetterState.postValue(ValidationStateResult.Success)
                }

                hasError = false
                for (error in listError) {
                    if(error is ErrorsValidationPassword.ErrorOneLowerLetterNotFound)
                        hasError = true
                }
                if(hasError == true) {
                    _passwordLowerLetterState.postValue(ValidationStateResult.Error)
                } else {
                    _passwordLowerLetterState.postValue(ValidationStateResult.Success)
                }

                hasError = false
                for (error in listError) {
                    if(error is ErrorsValidationPassword.ErrorOneSpecialCharacterNotFound)
                        hasError = true
                }
                if(hasError == true) {
                    _specialCharacterState.postValue(ValidationStateResult.Error)
                } else {
                    _specialCharacterState.postValue(ValidationStateResult.Success)
                }

                hasError = false
                for (error in listError) {
                    if(error is ErrorsValidationPassword.ErrorOneNumericCharacterNotFound)
                        hasError = true
                }
                if(hasError == true) {
                    _numericCharacterState.postValue(ValidationStateResult.Error)
                } else {
                    _numericCharacterState.postValue(ValidationStateResult.Success)
                }

                hasError = false
                for (error in listError) {
                    if(error is ErrorsValidationPassword.ErrorPasswordLengthNotMatch)
                        hasError = true
                }
                if(hasError == true) {
                   _passwordLengthState.postValue(ValidationStateResult.Error)
                } else {
                    _passwordLengthState.postValue(ValidationStateResult.Success)
                }
            }

            is PasswordValidationUseCaseResult.PasswordFieldEmpty ->
                _passwordFieldIsEmptyState.postValue(Unit)
        }
    }
}
