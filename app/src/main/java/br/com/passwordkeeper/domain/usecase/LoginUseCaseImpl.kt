package br.com.passwordkeeper.domain.usecase

import android.util.Log
import br.com.passwordkeeper.domain.result.SignInResult
import br.com.passwordkeeper.data.repository.AuthRepository

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

    }

    override suspend fun createUser(email: String, password: String) {

    }

    override suspend fun getCurrentUser() {

    }

}