package br.com.passwordkeeper.domain.usecases.sign_out

interface SignOutUseCase {

    suspend operator fun invoke(): SignOutUseCaseResult

}