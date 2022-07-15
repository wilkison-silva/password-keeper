package br.com.passwordkeeper.domain.result

sealed class GetCurrentUserResult {
    data class Success(val emailUser: String) : GetCurrentUserResult()
    object ErrorNoUserFound: GetCurrentUserResult()
}