package br.com.passwordkeeper.data.repository

import br.com.passwordkeeper.domain.result.CreateUserRepositoryResult
import br.com.passwordkeeper.domain.result.GetCurrentUserRepositoryResult
import br.com.passwordkeeper.domain.result.SignInRepositoryResult
import br.com.passwordkeeper.domain.result.SignOutRepositoryResult

interface AuthRepository {

    suspend fun signIn(email: String, password: String): SignInRepositoryResult

    suspend fun signOut(): SignOutRepositoryResult

    suspend fun createUser(email: String, password: String): CreateUserRepositoryResult

    suspend fun getCurrentUser(): GetCurrentUserRepositoryResult

}