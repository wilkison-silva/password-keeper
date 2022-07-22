package br.com.passwordkeeper.domain.usecase

import br.com.passwordkeeper.domain.result.usecase.GetAdviceUseCaseResult

interface AdviceUseCase {

    suspend fun getAdvice(): GetAdviceUseCaseResult
}