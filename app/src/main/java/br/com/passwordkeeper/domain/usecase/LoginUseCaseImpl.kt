package br.com.passwordkeeper.domain.usecase

import android.util.Log
import br.com.passwordkeeper.domain.result.SignInResult
import br.com.passwordkeeper.data.repository.AuthRepository
import br.com.passwordkeeper.domain.result.CreateUserResult
import br.com.passwordkeeper.domain.result.GetCurrentUserResult
import br.com.passwordkeeper.domain.result.SignOutResult

class LoginUseCaseImpl(
    private val authRepository: AuthRepository
) : LoginUseCase {

    override suspend fun signIn(email: String, password: String) {
        val signInResult: SignInResult = authRepository.signIn(email, password)
        when (signInResult) {
            is SignInResult.Success -> {
                signInResult.emailUser.let { emailUser: String ->
                    Log.i("LoginUseCaseImpl", "Email que fez login: $emailUser")
                }
            }
            is SignInResult.ErrorEmailOrPasswordIncorrect -> {
                Log.e("LoginUseCaseImpl", "Email ou senha inválida")
            }
            else -> {
                Log.e("LoginUseCaseImpl", "Erro desconhecido")
            }
        }
    }

    override suspend fun singOut() {
        when(authRepository.signOut()){
            is SignOutResult.Success -> {
                Log.i("LoginUseCaseImpl", "Sign Out com sucesso")
            }
            is SignOutResult.ErrorUnknown -> {
                Log.e("LoginUseCaseImpl", "Sign Out com erro desconhecido")
            }
        }
    }

    override suspend fun createUser(email: String, password: String) {
        when (authRepository.createUser(email, password)) {
            is CreateUserResult.Success -> {
                Log.i("LoginUseCaseImpl", "Usuário criado no firebase com sucesso")
            }
            is CreateUserResult.ErrorEmailAlreadyExists -> {
                Log.e("LoginUseCaseImpl", "Encontramos uma conta com esse email")
            }
            is CreateUserResult.ErrorEmailMalformed -> {
                Log.e("LoginUseCaseImpl", "E-mail mal formatado")
            }
            is CreateUserResult.ErrorWeakPassword -> {
                Log.e("LoginUseCaseImpl", "Senha muito fraca")
            }
            is CreateUserResult.ErrorUnknown -> {
                Log.e("LoginUseCaseImpl", "Erro desconhecido")
            }
        }
    }

    override suspend fun getCurrentUser() {
        val getCurrentUserResult = authRepository.getCurrentUser()
        when (getCurrentUserResult) {
            is GetCurrentUserResult.Success -> {
                val emailUser = getCurrentUserResult.emailUser
                Log.i("LoginUseCaseImpl", "usuário atual: $emailUser")
            }
            is GetCurrentUserResult.ErrorNoUserFound -> {
                Log.e("LoginUseCaseImpl", "Sem usuário logado no sistema")
            }
        }
    }

}