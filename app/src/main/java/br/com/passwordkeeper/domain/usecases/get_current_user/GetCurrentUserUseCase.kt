package br.com.passwordkeeper.domain.usecases.get_current_user

import br.com.passwordkeeper.domain.result.usecase.GetCurrentUserUseCaseResult

interface GetCurrentUserUseCase {

    suspend operator fun invoke(): GetCurrentUserUseCaseResult

}