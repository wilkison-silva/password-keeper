package br.com.passwordkeeper.di

import br.com.passwordkeeper.data.repository.AuthRepository
import br.com.passwordkeeper.data.repository.FirebaseAuthRepositoryImpl
import br.com.passwordkeeper.domain.usecase.LoginUseCase
import br.com.passwordkeeper.domain.usecase.LoginUseCaseImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.koin.dsl.module

val firebaseModule = module {
    single<FirebaseAuth> { Firebase.auth }
}

val repositoryModule = module {
    single<AuthRepository> { FirebaseAuthRepositoryImpl(get<FirebaseAuth>()) }
}

val useCaseModule = module {
    single<LoginUseCase> { LoginUseCaseImpl(get<AuthRepository>()) }
}