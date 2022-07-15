package br.com.passwordkeeper.data.repository

import br.com.passwordkeeper.domain.result.CreateUserResult
import br.com.passwordkeeper.domain.result.GetCurrentUserResult
import br.com.passwordkeeper.domain.result.SignInResult
import br.com.passwordkeeper.domain.result.SignOutResult

interface AuthRepository {

    suspend fun signIn(email: String, password: String): SignInResult

    suspend fun signOut(): SignOutResult

    suspend fun createUser(email: String, password: String): CreateUserResult

    suspend fun getCurrentUser(): GetCurrentUserResult

}