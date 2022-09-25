package br.com.passwordkeeper.domain.usecases.get_advice

interface GetAdviceUseCase {

    suspend operator fun invoke(): GetAdviceUseCaseResult
}