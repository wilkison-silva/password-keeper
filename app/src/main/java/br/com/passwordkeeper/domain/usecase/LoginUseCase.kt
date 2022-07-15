package br.com.passwordkeeper.domain.usecase

import br.com.passwordkeeper.domain.result.SignInResult
import br.com.passwordkeeper.data.repository.FirebaseAuthRepository

class LoginUseCase(
    private val firebaseAuthRepository: FirebaseAuthRepository
) {

    fun signIn(email: String, password: String) {
        firebaseAuthRepository.signIn(email, password) { signInResult: SignInResult ->
            when (signInResult) {
                is SignInResult.Success -> {
                    signInResult.emailUser.let { emailUser: String ->

                    }
                }
                is SignInResult.ErrorEmailOrPasswordIncorrect -> {
                    //TODO return a error about email or password to viewmodel
                }
                else -> {
                    //TODO return error unknown to viewmodel
                }
            }
        }
    }


    fun singOut() {
        firebaseAuthRepository.signOut()
    }

    fun createUser(email: String, password: String) {
        firebaseAuthRepository.createUser(email, password) {

        }
    }

    fun getCurrentUser() {
        firebaseAuthRepository.getCurrentUser {

        }
    }

}