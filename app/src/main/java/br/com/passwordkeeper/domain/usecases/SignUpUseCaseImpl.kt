package br.com.passwordkeeper.domain.usecases

import br.com.passwordkeeper.domain.repository.AuthRepository
import br.com.passwordkeeper.domain.result.repository.CreateUserRepositoryResult
import br.com.passwordkeeper.domain.result.usecase.CreateUserUseCaseResult

class SignUpUseCaseImpl(
    private val authRepository: AuthRepository
) : SignUpUseCase {

    override suspend fun createUser(
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