package br.com.passwordkeeper.domain.usecases.sign_in

import br.com.passwordkeeper.domain.mapper.UserDomainMapper
import br.com.passwordkeeper.domain.repository.AuthRepository
import br.com.passwordkeeper.domain.repository.SignInRepositoryResult

class SignInUseCaseImpl(
    private val authRepository: AuthRepository,
    private val userDomainMapper: UserDomainMapper
) : SignInUseCase {

    override suspend operator fun invoke(email: String, password: String): SignInUseCaseResult {
        return when (
            val signInRepositoryResult: SignInRepositoryResult = authRepository
                .signIn(email, password)) {
            is SignInRepositoryResult.Success -> {
                val userDomain = signInRepositoryResult.userDomain
                val userView = userDomainMapper.transform(userDomain)
                SignInUseCaseResult.Success(userView)
            }
            is SignInRepositoryResult.ErrorEmailOrPasswordIncorrect -> {
                SignInUseCaseResult.ErrorEmailOrPasswordWrong
            }
            else -> {
                SignInUseCaseResult.ErrorUnknown
            }
        }
    }

}