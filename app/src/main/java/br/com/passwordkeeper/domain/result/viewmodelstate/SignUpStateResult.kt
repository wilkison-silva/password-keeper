package br.com.passwordkeeper.domain.result.viewmodelstate

sealed class SignUpStateResult {
    object Success : SignUpStateResult()
    object ErrorUnknown: SignUpStateResult()
}