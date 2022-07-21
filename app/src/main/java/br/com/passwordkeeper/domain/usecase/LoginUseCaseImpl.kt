package br.com.passwordkeeper.domain.usecase

import br.com.passwordkeeper.data.repository.AuthRepository
import br.com.passwordkeeper.domain.model.User
import br.com.passwordkeeper.domain.result.*

class LoginUseCaseImpl(
    private val authRepository: AuthRepository
) : LoginUseCase {

    override suspend fun signIn(email: String, password: String): SignInUseCaseResult {
        return when (
            val signInRepositoryResult: SignInRepositoryResult = authRepository
                .signIn(email, password)) {
            is SignInRepositoryResult.Success -> {
                val user: User = signInRepositoryResult.user
                SignInUseCaseResult.Success(user.convertToUserView())
            }
            is SignInRepositoryResult.ErrorEmailOrPasswordIncorrect -> {
                SignInUseCaseResult.ErrorEmailOrPasswordWrong
            }
            else -> {
                SignInUseCaseResult.ErrorUnknown
            }
        }
    }

    override suspend fun singOut(): SignOutUseCaseResult {
        return when (authRepository.signOut()) {
            is SignOutRepositoryResult.Success -> {
                SignOutUseCaseResult.Success
            }
            is SignOutRepositoryResult.ErrorUnknown -> {
                SignOutUseCaseResult.ErrorUnknown
            }
        }
    }

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

    override suspend fun getCurrentUser(): GetCurrentUserUseCaseResult {
        return when (val getCurrentUserRepositoryResult = authRepository.getCurrentUser()) {
            is GetCurrentUserRepositoryResult.Success -> {
                val user = getCurrentUserRepositoryResult.user
                GetCurrentUserUseCaseResult.Success(user.convertToUserView())
            }
            is GetCurrentUserRepositoryResult.ErrorNoUserRepositoryFound -> {
                GetCurrentUserUseCaseResult.ErrorUnknown
            }
        }
    }

}