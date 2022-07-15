package br.com.passwordkeeper.data.repository.source.web.result

sealed class SignInResult {
    data class Success(val emailUser: String) : SignInResult()
    object ErrorUserNotFound : SignInResult()
    object ErrorEmailOrPasswordIncorrect: SignInResult()
    object ErrorUnknown: SignInResult()
}