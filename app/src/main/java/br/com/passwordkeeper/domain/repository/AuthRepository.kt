package br.com.passwordkeeper.domain.repository

import br.com.passwordkeeper.domain.result.repository.CreateUserRepositoryResult
import br.com.passwordkeeper.domain.result.repository.GetCurrentUserRepositoryResult
import br.com.passwordkeeper.domain.result.repository.SignInRepositoryResult
import br.com.passwordkeeper.domain.result.repository.SignOutRepositoryResult

interface AuthRepository {

    suspend fun signIn(email: String, password: String): SignInRepositoryResult

    suspend fun signOut(): SignOutRepositoryResult

    suspend fun createUser(name: String, email: String, password: String): CreateUserRepositoryResult

    suspend fun getCurrentUser(): GetCurrentUserRepositoryResult

}