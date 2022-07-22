package br.com.passwordkeeper.domain.result.repository

import br.com.passwordkeeper.domain.model.UserDomain

sealed class SignInRepositoryResult {
    data class Success(val userDomain: UserDomain) : SignInRepositoryResult()
    object ErrorUserNotFound : SignInRepositoryResult()
    object ErrorEmailOrPasswordIncorrect: SignInRepositoryResult()
    object ErrorUnknown: SignInRepositoryResult()
}