package br.com.passwordkeeper.domain.usecase

import br.com.passwordkeeper.domain.result.usecase.GetCurrentUserUseCaseResult
import br.com.passwordkeeper.domain.result.usecase.SignInUseCaseResult
import br.com.passwordkeeper.domain.result.usecase.SignUpUseCaseResult

interface SignInUseCase {

    suspend fun signIn(email: String, password: String): SignInUseCaseResult

    suspend fun signUp(): SignUpUseCaseResult

    suspend fun getCurrentUser(): GetCurrentUserUseCaseResult

}