package br.com.passwordkeeper.domain.usecase

import br.com.passwordkeeper.domain.result.CreateUserUseCaseResult
import br.com.passwordkeeper.domain.result.GetCurrentUserUseCaseResult
import br.com.passwordkeeper.domain.result.SignInUseCaseResult
import br.com.passwordkeeper.domain.result.SignOutUseCaseResult

interface LoginUseCase {

    suspend fun signIn(email: String, password: String): SignInUseCaseResult

    suspend fun singOut(): SignOutUseCaseResult

    suspend fun createUser(email: String, password: String): CreateUserUseCaseResult

    suspend fun getCurrentUser(): GetCurrentUserUseCaseResult

}