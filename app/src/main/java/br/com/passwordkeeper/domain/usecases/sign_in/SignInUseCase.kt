package br.com.passwordkeeper.domain.usecases.sign_in

import br.com.passwordkeeper.domain.result.usecase.SignInUseCaseResult

interface SignInUseCase {

    suspend operator fun invoke(email: String, password: String): SignInUseCaseResult

}