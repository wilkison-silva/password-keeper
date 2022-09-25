package br.com.passwordkeeper.domain.usecases.password_validation

sealed class PasswordValidationUseCaseResult {
    object PasswordFieldEmpty : PasswordValidationUseCaseResult()
    data class ErrorsFound(
        val errorList: List<ErrorsValidationPassword>,
    ) : PasswordValidationUseCaseResult()
}