package br.com.passwordkeeper.domain.usecase

interface LoginUseCase {

    suspend fun signIn(email: String, password: String)

    suspend fun singOut()

    suspend fun createUser(email: String, password: String)

    suspend fun getCurrentUser()

}