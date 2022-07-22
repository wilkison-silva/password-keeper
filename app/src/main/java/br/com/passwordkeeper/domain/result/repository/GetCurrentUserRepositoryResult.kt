package br.com.passwordkeeper.domain.result.repository

import br.com.passwordkeeper.domain.model.UserDomain

sealed class GetCurrentUserRepositoryResult {
    data class Success(val userDomain: UserDomain) : GetCurrentUserRepositoryResult()
    object ErrorNoUserRepositoryFound: GetCurrentUserRepositoryResult()
}