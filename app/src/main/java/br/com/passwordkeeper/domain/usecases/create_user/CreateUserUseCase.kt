package br.com.passwordkeeper.domain.usecases.create_user

interface CreateUserUseCase {

    suspend operator fun invoke(name: String, email: String, password: String): CreateUserUseCaseResult

}