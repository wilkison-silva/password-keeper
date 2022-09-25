package br.com.passwordkeeper.domain.usecases.create_user

import br.com.passwordkeeper.domain.result.usecase.CreateUserUseCaseResult

interface CreateUserUseCase {

    suspend operator fun invoke(name: String, email: String, password: String): CreateUserUseCaseResult

}