package br.com.passwordkeeper.domain.result.viewmodelstate

import br.com.passwordkeeper.domain.model.UserView

sealed class SignInStateResult {
    data class Success(val userView: UserView) : SignInStateResult()
    object ErrorEmailOrPasswordWrong: SignInStateResult()
    object ErrorUnknown: SignInStateResult()
    object EmptyState: SignInStateResult()
    object Loading: SignInStateResult()
}