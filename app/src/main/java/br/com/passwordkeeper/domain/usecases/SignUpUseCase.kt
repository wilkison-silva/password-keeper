package br.com.passwordkeeper.domain.usecases

import br.com.passwordkeeper.domain.result.usecase.CreateUserUseCaseResult

interface SignUpUseCase {

    suspend fun createUser(name: String, email: String, password: String): CreateUserUseCaseResult

}