package br.com.passwordkeeper.domain.usecases.sign_in

interface SignInUseCase {

    suspend operator fun invoke(email: String, password: String): SignInUseCaseResult

}