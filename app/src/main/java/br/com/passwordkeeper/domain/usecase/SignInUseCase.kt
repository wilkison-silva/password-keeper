package br.com.passwordkeeper.domain.usecase

import br.com.passwordkeeper.domain.result.GetCurrentUserUseCaseResult
import br.com.passwordkeeper.domain.result.SignInUseCaseResult
import br.com.passwordkeeper.domain.result.SignOutUseCaseResult

interface SignInUseCase {

    suspend fun signIn(email: String, password: String): SignInUseCaseResult

    suspend fun singOut(): SignOutUseCaseResult

    suspend fun getCurrentUser(): GetCurrentUserUseCaseResult

}