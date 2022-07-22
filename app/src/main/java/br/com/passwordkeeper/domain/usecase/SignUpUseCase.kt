package br.com.passwordkeeper.domain.usecase

import br.com.passwordkeeper.domain.result.CreateUserUseCaseResult

interface SignUpUseCase {

    suspend fun createUser(name: String, email: String, password: String): CreateUserUseCaseResult

}