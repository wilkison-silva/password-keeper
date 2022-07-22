package br.com.passwordkeeper.domain.usecase

import br.com.passwordkeeper.data.repository.AuthRepository
import br.com.passwordkeeper.domain.result.CreateUserRepositoryResult
import br.com.passwordkeeper.domain.result.CreateUserUseCaseResult

class SignUpUseCaseImpl(
    private val authRepository: AuthRepository
) : SignUpUseCase {

    override suspend fun createUser(email: String, password: String): CreateUserUseCaseResult {
        return when (authRepository.createUser(email, password)) {
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