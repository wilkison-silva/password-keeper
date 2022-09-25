package br.com.passwordkeeper.domain.usecases.get_current_user

import br.com.passwordkeeper.domain.mapper.UserDomainMapper
import br.com.passwordkeeper.domain.repository.AuthRepository
import br.com.passwordkeeper.domain.repository.GetCurrentUserRepositoryResult

class GetCurrentUserUseCaseImpl(
    private val authRepository: AuthRepository,
    private val userDomainMapper: UserDomainMapper
) : GetCurrentUserUseCase {

    override suspend operator fun invoke(): GetCurrentUserUseCaseResult {
        return when (val getCurrentUserRepositoryResult = authRepository.getCurrentUser()) {
            is GetCurrentUserRepositoryResult.Success -> {
                val userDomain = getCurrentUserRepositoryResult.userDomain
                val userView = userDomainMapper.transform(userDomain)
                GetCurrentUserUseCaseResult.Success(userView)
            }
            is GetCurrentUserRepositoryResult.ErrorNoUserRepositoryFound -> {
                GetCurrentUserUseCaseResult.ErrorUnknown
            }
        }
    }

}