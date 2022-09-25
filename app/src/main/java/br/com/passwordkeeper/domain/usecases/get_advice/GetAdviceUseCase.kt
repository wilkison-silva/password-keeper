package br.com.passwordkeeper.domain.usecases.get_advice

import br.com.passwordkeeper.domain.result.usecase.GetAdviceUseCaseResult

interface GetAdviceUseCase {

    suspend operator fun invoke(): GetAdviceUseCaseResult
}