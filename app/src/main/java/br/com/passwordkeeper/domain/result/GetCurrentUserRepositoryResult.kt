package br.com.passwordkeeper.domain.result

sealed class GetCurrentUserRepositoryResult {
    data class Success(val emailUser: String) : GetCurrentUserRepositoryResult()
    object ErrorNoUserRepositoryFound: GetCurrentUserRepositoryResult()
}