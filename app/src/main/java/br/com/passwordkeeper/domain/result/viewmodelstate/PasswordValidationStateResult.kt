package br.com.passwordkeeper.domain.result.viewmodelstate

import br.com.passwordkeeper.domain.result.usecase.ErrorsValidationPassword

sealed class PasswordValidationStateResult {
    object Success : PasswordValidationStateResult()
    data class ErrorsFound(
        val errorList: List<ErrorsValidationPassword>,
    ) : PasswordValidationStateResult()
}