package br.com.passwordkeeper.domain.usecase

import br.com.passwordkeeper.data.repository.AuthRepository
import br.com.passwordkeeper.domain.model.UserDomain
import br.com.passwordkeeper.domain.result.repository.GetCurrentUserRepositoryResult
import br.com.passwordkeeper.domain.result.repository.SignInRepositoryResult
import br.com.passwordkeeper.domain.result.repository.SignOutRepositoryResult
import br.com.passwordkeeper.domain.result.usecase.GetCurrentUserUseCaseResult
import br.com.passwordkeeper.domain.result.usecase.SignInUseCaseResult
import br.com.passwordkeeper.domain.result.usecase.SignOutUseCaseResult

class SignInUseCaseImpl(
    private val authRepository: AuthRepository
) : SignInUseCase {

    override suspend fun signIn(email: String, password: String): SignInUseCaseResult {
        return when (
            val signInRepositoryResult: SignInRepositoryResult = authRepository
                .signIn(email, password)) {
            is SignInRepositoryResult.Success -> {
                val userDomain: UserDomain = signInRepositoryResult.userDomain
                SignInUseCaseResult.Success(userDomain.convertToUserView())
            }
            is SignInRepositoryResult.ErrorEmailOrPasswordIncorrect -> {
                SignInUseCaseResult.ErrorEmailOrPasswordWrong
            }
            else -> {
                SignInUseCaseResult.ErrorUnknown
            }
        }
    }

    override suspend fun signOut(): SignOutUseCaseResult {
        return when (authRepository.signOut()) {
            is SignOutRepositoryResult.Success -> {
                SignOutUseCaseResult.Success
            }
            is SignOutRepositoryResult.ErrorUnknown -> {
                SignOutUseCaseResult.ErrorUnknown
            }
        }
    }

    override suspend fun getCurrentUser(): GetCurrentUserUseCaseResult {
        return when (val getCurrentUserRepositoryResult = authRepository.getCurrentUser()) {
            is GetCurrentUserRepositoryResult.Success -> {
                val user = getCurrentUserRepositoryResult.userDomain
                GetCurrentUserUseCaseResult.Success(user.convertToUserView())
            }
            is GetCurrentUserRepositoryResult.ErrorNoUserRepositoryFound -> {
                GetCurrentUserUseCaseResult.ErrorUnknown
            }
        }
    }

}