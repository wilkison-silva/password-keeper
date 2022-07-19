package br.com.passwordkeeper.domain.usecase

import br.com.passwordkeeper.domain.result.GetAdviceUseCaseResult

interface AdviceUseCase {

    suspend fun getAdvice(): GetAdviceUseCaseResult
}