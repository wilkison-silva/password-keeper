package br.com.passwordkeeper.domain.usecase

import br.com.passwordkeeper.data.source.web.result.FirebaseAuthSignInResult
import br.com.passwordkeeper.domain.repository.FirebaseAuthRepository

class LoginUseCase(
    private val firebaseAuthRepository: FirebaseAuthRepository
) {

    fun signIn(email: String, password: String) {
        firebaseAuthRepository.signIn(email, password) { firebaseAuthSignInResult ->
            when (firebaseAuthSignInResult) {
                is FirebaseAuthSignInResult.Success -> {
                    firebaseAuthSignInResult.firebaseUser.email?.let {
                        //TODO return user email to viewmodel, and in case of null email return
                        //TODO user not found
                    }
                }
                is FirebaseAuthSignInResult.ErrorEmailOrPasswordIncorrect -> {
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