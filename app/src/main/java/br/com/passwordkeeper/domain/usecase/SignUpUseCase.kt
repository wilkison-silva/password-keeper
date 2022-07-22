package br.com.passwordkeeper.domain.usecase

import br.com.passwordkeeper.domain.result.CreateUserUseCaseResult

interface SignUpUseCase {

    suspend fun createUser(email: String, password: String): CreateUserUseCaseResult

}