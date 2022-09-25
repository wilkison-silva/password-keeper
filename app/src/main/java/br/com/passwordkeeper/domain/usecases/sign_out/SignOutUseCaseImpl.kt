package br.com.passwordkeeper.domain.usecases.sign_out

import br.com.passwordkeeper.domain.repository.AuthRepository
import br.com.passwordkeeper.domain.repository.SignOutRepositoryResult

class SignOutUseCaseImpl(
    private val authRepository: AuthRepository
) : SignOutUseCase {

    override suspend operator fun invoke(): SignOutUseCaseResult {
        return when (authRepository.signOut()) {
            is SignOutRepositoryResult.Success -> {
                SignOutUseCaseResult.Success
            }
            is SignOutRepositoryResult.ErrorUnknown -> {
                SignOutUseCaseResult.ErrorUnknown
            }
        }
    }

}