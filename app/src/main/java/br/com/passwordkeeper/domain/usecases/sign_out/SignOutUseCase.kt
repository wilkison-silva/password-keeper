package br.com.passwordkeeper.domain.usecases.sign_out

import br.com.passwordkeeper.domain.result.usecase.SignOutUseCaseResult

interface SignOutUseCase {

    suspend operator fun invoke(): SignOutUseCaseResult

}