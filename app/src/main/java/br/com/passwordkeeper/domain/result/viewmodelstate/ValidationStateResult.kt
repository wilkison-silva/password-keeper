package br.com.passwordkeeper.domain.result.viewmodelstate

import br.com.passwordkeeper.domain.result.usecase.ErrorsValidationPassword

sealed class ValidationStateResult {
    object Success : ValidationStateResult()
    object Error : ValidationStateResult()
}