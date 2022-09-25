package br.com.passwordkeeper.domain.usecases.sign_in

import br.com.passwordkeeper.presentation.model.UserView

sealed class SignInUseCaseResult {
    data class Success(val userView: UserView) : SignInUseCaseResult()
    object ErrorEmailOrPasswordWrong: SignInUseCaseResult()
    object ErrorUnknown: SignInUseCaseResult()
}