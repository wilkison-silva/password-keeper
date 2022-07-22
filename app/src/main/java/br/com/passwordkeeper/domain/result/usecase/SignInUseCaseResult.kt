package br.com.passwordkeeper.domain.result.usecase

import br.com.passwordkeeper.domain.model.UserView

sealed class SignInUseCaseResult {
    data class Success(val userView: UserView) : SignInUseCaseResult()
    object ErrorEmailOrPasswordWrong: SignInUseCaseResult()
    object ErrorUnknown: SignInUseCaseResult()
}