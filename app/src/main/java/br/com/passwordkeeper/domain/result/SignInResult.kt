package br.com.passwordkeeper.domain.result

sealed class SignInResult {
    data class Success(val emailUser: String) : SignInResult()
    object ErrorUserNotFound : SignInResult()
    object ErrorEmailOrPasswordIncorrect: SignInResult()
    object ErrorUnknown: SignInResult()
}