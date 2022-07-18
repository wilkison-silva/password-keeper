package br.com.passwordkeeper.domain.result

sealed class SignOutResult {
    object Success : SignOutResult()
    object ErrorUnknown: SignOutResult()
}