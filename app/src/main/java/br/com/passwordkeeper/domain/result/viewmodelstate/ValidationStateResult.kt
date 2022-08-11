package br.com.passwordkeeper.domain.result.viewmodelstate

sealed class ValidationStateResult {
    object Success : ValidationStateResult()
    object Error : ValidationStateResult()
}