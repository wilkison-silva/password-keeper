package br.com.passwordkeeper.domain.usecases.get_current_user

interface GetCurrentUserUseCase {

    suspend operator fun invoke(): GetCurrentUserUseCaseResult

}