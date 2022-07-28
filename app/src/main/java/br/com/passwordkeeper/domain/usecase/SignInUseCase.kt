package br.com.passwordkeeper.domain.usecase

import br.com.passwordkeeper.domain.result.usecase.GetCurrentUserUseCaseResult
import br.com.passwordkeeper.domain.result.usecase.SignInUseCaseResult
import br.com.passwordkeeper.domain.result.usecase.SignOutUseCaseResult

interface SignInUseCase {

    suspend fun signIn(email: String, password: String): SignInUseCaseResult

    suspend fun signOut(): SignOutUseCaseResult

    suspend fun getCurrentUser(): GetCurrentUserUseCaseResult

}