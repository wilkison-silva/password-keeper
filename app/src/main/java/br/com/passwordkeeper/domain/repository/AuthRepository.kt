package br.com.passwordkeeper.domain.repository

interface AuthRepository {

    suspend fun signIn(email: String, password: String): SignInRepositoryResult

    suspend fun signOut(): SignOutRepositoryResult

    suspend fun createUser(name: String, email: String, password: String): CreateUserRepositoryResult

    suspend fun getCurrentUser(): GetCurrentUserRepositoryResult

}