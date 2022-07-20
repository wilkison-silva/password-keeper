package br.com.passwordkeeper.domain.result

import br.com.passwordkeeper.domain.model.User

sealed class SignInRepositoryResult {
    data class Success(val user: User) : SignInRepositoryResult()
    object ErrorUserNotFound : SignInRepositoryResult()
    object ErrorEmailOrPasswordIncorrect: SignInRepositoryResult()
    object ErrorUnknown: SignInRepositoryResult()
}