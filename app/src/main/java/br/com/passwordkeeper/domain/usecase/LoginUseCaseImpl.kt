package br.com.passwordkeeper.domain.usecase

import android.util.Log
import br.com.passwordkeeper.data.repository.AuthRepository
import br.com.passwordkeeper.domain.model.User
import br.com.passwordkeeper.domain.result.*

class LoginUseCaseImpl(
    private val authRepository: AuthRepository
) : LoginUseCase {

    override suspend fun signIn(email: String, password: String): SignInUseCaseResult {
        val signInRepositoryResult: SignInRepositoryResult = authRepository.signIn(email, password)
        return when (signInRepositoryResult) {
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

    override suspend fun singOut() {
        when(authRepository.signOut()){
            is SignOutRepositoryResult.Success -> {
                Log.i("LoginUseCaseImpl", "Sign Out com sucesso")
            }
            is SignOutRepositoryResult.ErrorUnknown -> {
                Log.e("LoginUseCaseImpl", "Sign Out com erro desconhecido")
            }
        }
    }

    override suspend fun createUser(email: String, password: String) {
        when (authRepository.createUser(email, password)) {
            is CreateUserRepositoryResult.Success -> {
                Log.i("LoginUseCaseImpl", "Usuário criado no firebase com sucesso")
            }
            is CreateUserRepositoryResult.ErrorEmailAlreadyExists -> {
                Log.e("LoginUseCaseImpl", "Encontramos uma conta com esse email")
            }
            is CreateUserRepositoryResult.ErrorEmailMalformed -> {
                Log.e("LoginUseCaseImpl", "E-mail mal formatado")
            }
            is CreateUserRepositoryResult.ErrorWeakPassword -> {
                Log.e("LoginUseCaseImpl", "Senha muito fraca")
            }
            is CreateUserRepositoryResult.ErrorUnknown -> {
                Log.e("LoginUseCaseImpl", "Erro desconhecido")
            }
        }
    }

    override suspend fun getCurrentUser() {
        val getCurrentUserResult = authRepository.getCurrentUser()
        when (getCurrentUserResult) {
            is GetCurrentUserRepositoryResult.Success -> {
                val emailUser = getCurrentUserResult.emailUser
                Log.i("LoginUseCaseImpl", "usuário atual: $emailUser")
            }
            is GetCurrentUserRepositoryResult.ErrorNoUserRepositoryFound -> {
                Log.e("LoginUseCaseImpl", "Sem usuário logado no sistema")
            }
        }
    }

}