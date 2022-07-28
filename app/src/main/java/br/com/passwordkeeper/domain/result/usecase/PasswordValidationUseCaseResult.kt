package br.com.passwordkeeper.domain.result.usecase

sealed class PasswordValidationUseCaseResult {
    object Success : PasswordValidationUseCaseResult()
    data class ErrorsFound(
        val errorList: List<ErrorsValidationPassword>,
    ) : PasswordValidationUseCaseResult()
}