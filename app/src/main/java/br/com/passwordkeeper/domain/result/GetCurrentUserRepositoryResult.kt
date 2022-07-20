package br.com.passwordkeeper.domain.result

import br.com.passwordkeeper.domain.model.User

sealed class GetCurrentUserRepositoryResult {
    data class Success(val user: User) : GetCurrentUserRepositoryResult()
    object ErrorNoUserRepositoryFound: GetCurrentUserRepositoryResult()
}