package br.com.passwordkeeper.domain.result.usecase

sealed class PasswordValidationUseCaseResult {
    object PasswordFieldEmpty : PasswordValidationUseCaseResult()
    data class ErrorsFound(
        val errorList: List<ErrorsValidationPassword>,
    ) : PasswordValidationUseCaseResult()
}