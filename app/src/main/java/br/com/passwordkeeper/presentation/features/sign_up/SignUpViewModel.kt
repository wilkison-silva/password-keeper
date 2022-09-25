package br.com.passwordkeeper.presentation.features.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.passwordkeeper.domain.usecases.create_user.CreateUserUseCase
import br.com.passwordkeeper.domain.usecases.create_user.CreateUserUseCaseResult
import br.com.passwordkeeper.domain.usecases.form_validation_sign_up.FormValidationSignUpUseCase
import br.com.passwordkeeper.domain.usecases.form_validation_sign_up.FormValidationSignUpUseCaseResult
import br.com.passwordkeeper.domain.usecases.password_validation.ErrorsValidationPassword
import br.com.passwordkeeper.domain.usecases.password_validation.ErrorsValidationPassword.*
import br.com.passwordkeeper.domain.usecases.password_validation.PasswordValidationUseCase
import br.com.passwordkeeper.domain.usecases.password_validation.PasswordValidationUseCaseResult
import br.com.passwordkeeper.presentation.features.sign_up.states.CreateUserState
import br.com.passwordkeeper.presentation.features.sign_up.states.FormValidationSignUpState
import br.com.passwordkeeper.presentation.features.sign_up.states.ValidationState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val createUserUseCase: CreateUserUseCase,
    private val formValidationSignUpUseCase: FormValidationSignUpUseCase,
    private val passwordValidationUseCase: PasswordValidationUseCase,
) : ViewModel() {

    private val _createUserState = MutableStateFlow<CreateUserState>(CreateUserState.EmptyState)
    val createUserState = _createUserState.asStateFlow()

    private val _formValidationState =
        MutableStateFlow<FormValidationSignUpState>(FormValidationSignUpState.EmptyState)
    val formValidationState = _formValidationState.asStateFlow()

    private val _passwordFieldIsEmptyState = MutableStateFlow(true)
    val passwordFieldIsEmptyState = _passwordFieldIsEmptyState

    private val _passwordUpperLetterState =
        MutableStateFlow<ValidationState>(ValidationState.EmptyState)
    val passwordUpperLetterState = _passwordUpperLetterState

    private val _passwordLowerLetterState =
        MutableStateFlow<ValidationState>(ValidationState.EmptyState)
    val passwordLowerLetterState = _passwordLowerLetterState

    private val _passwordSpecialLetterState =
        MutableStateFlow<ValidationState>(ValidationState.EmptyState)
    val passwordSpecialCharacterState = _passwordSpecialLetterState

    private val _passwordNumericLetterState =
        MutableStateFlow<ValidationState>(ValidationState.EmptyState)
    val passwordNumericCharactersState = _passwordNumericLetterState

    private val _passwordLengthState = MutableStateFlow<ValidationState>(ValidationState.EmptyState)
    val passwordLengthState = _passwordLengthState

    fun updateFormValidationState(
        name: String,
        email: String,
        password: String,
        confirmedPassword: String,
    ) {
        when (formValidationSignUpUseCase(name, email, password, confirmedPassword)) {
            is FormValidationSignUpUseCaseResult.ErrorEmailIsBlank ->
                _formValidationState.value = FormValidationSignUpState.ErrorEmailIsBlank
            is FormValidationSignUpUseCaseResult.ErrorEmailMalFormed ->
                _formValidationState.value = FormValidationSignUpState.ErrorEmailMalFormed
            is FormValidationSignUpUseCaseResult.ErrorNameIsBlank ->
                _formValidationState.value = FormValidationSignUpState.ErrorNameIsBlank
            is FormValidationSignUpUseCaseResult.ErrorPasswordIsBlank ->
                _formValidationState.value = FormValidationSignUpState.ErrorPasswordIsBlank
            is FormValidationSignUpUseCaseResult.ErrorPasswordTooWeak ->
                _formValidationState.value = FormValidationSignUpState.ErrorPasswordTooWeak
            is FormValidationSignUpUseCaseResult.ErrorPasswordsDoNotMatch ->
                _formValidationState.value = FormValidationSignUpState.ErrorPasswordsDoNotMatch
            is FormValidationSignUpUseCaseResult.Success ->
                _formValidationState.value =
                    FormValidationSignUpState.Success(name, email, password)
        }

    }

    fun updateSignUpState(name: String, email: String, password: String) {
        viewModelScope.launch {
            when (createUserUseCase(name, email, password)) {
                CreateUserUseCaseResult.ErrorEmailAlreadyExists ->
                    _createUserState.value = CreateUserState.ErrorEmailAlreadyExists
                CreateUserUseCaseResult.ErrorEmailMalformed ->
                    _createUserState.value = CreateUserState.ErrorEmailMalformed
                CreateUserUseCaseResult.ErrorUnknown ->
                    _createUserState.value = CreateUserState.ErrorUnknown
                CreateUserUseCaseResult.ErrorWeakPassword ->
                    _createUserState.value = CreateUserState.ErrorWeakPassword
                CreateUserUseCaseResult.Success ->
                    _createUserState.value = CreateUserState.Success
            }
        }
    }

    fun updateStatesToEmptyState() {
        _createUserState.value = CreateUserState.EmptyState
        _formValidationState.value = FormValidationSignUpState.EmptyState
    }

    private fun setPasswordState(
        errorList: List<ErrorsValidationPassword>,
        error: ErrorsValidationPassword,
        state: MutableStateFlow<ValidationState>,
    ) {
        errorList.contains(error).let {
            if (it)
                state.value = ValidationState.Error
            else
                state.value = ValidationState.Success
        }
    }

    fun updatePasswordValidation(password: String) {
        when (val passwordValidationUseCaseResult =
            passwordValidationUseCase(password)) {
            is PasswordValidationUseCaseResult.ErrorsFound -> {
                _passwordFieldIsEmptyState.value = false
                passwordValidationUseCaseResult.errorList.let {
                    setPasswordState(it, ErrorOneUpperLetterNotFound, _passwordUpperLetterState)
                    setPasswordState(it, ErrorOneLowerLetterNotFound, _passwordLowerLetterState)
                    setPasswordState(it, ErrorOneSpecialLetterNotFound, _passwordSpecialLetterState)
                    setPasswordState(it, ErrorOneNumericLetterNotFound, _passwordNumericLetterState)
                    setPasswordState(it, ErrorPasswordSizeNotMatch, _passwordLengthState)
                }
            }
            is PasswordValidationUseCaseResult.PasswordFieldEmpty -> {
                _passwordFieldIsEmptyState.value = true
                _passwordUpperLetterState.value = ValidationState.EmptyState
                _passwordLowerLetterState.value = ValidationState.EmptyState
                _passwordSpecialLetterState.value = ValidationState.EmptyState
                _passwordNumericLetterState.value = ValidationState.EmptyState
                _passwordLengthState.value = ValidationState.EmptyState
            }
        }
    }
}
