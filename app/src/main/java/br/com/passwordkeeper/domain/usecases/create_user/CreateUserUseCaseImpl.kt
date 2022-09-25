package br.com.passwordkeeper.domain.usecases.create_user

import br.com.passwordkeeper.domain.repository.AuthRepository
import br.com.passwordkeeper.domain.result.repository.CreateUserRepositoryResult
import br.com.passwordkeeper.domain.result.usecase.CreateUserUseCaseResult

class CreateUserUseCaseImpl(
    private val authRepository: AuthRepository
) : CreateUserUseCase {

    override suspend operator fun invoke(
        name: String,
        email: String,
        password: String
    ): CreateUserUseCaseResult {
        return when (authRepository.createUser(name, email, password)) {
            is CreateUserRepositoryResult.Success -> {
                CreateUserUseCaseResult.Success
            }
            is CreateUserRepositoryResult.ErrorEmailAlreadyExists -> {
                CreateUserUseCaseResult.ErrorEmailAlreadyExists
            }
            is CreateUserRepositoryResult.ErrorEmailMalformed -> {
                CreateUserUseCaseResult.ErrorEmailMalformed
            }
            is CreateUserRepositoryResult.ErrorWeakPassword -> {
                CreateUserUseCaseResult.ErrorWeakPassword
            }
            is CreateUserRepositoryResult.ErrorUnknown -> {
                CreateUserUseCaseResult.ErrorUnknown
            }
        }
    }

}