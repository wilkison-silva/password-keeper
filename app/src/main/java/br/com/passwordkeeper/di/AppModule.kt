package br.com.passwordkeeper.di

import br.com.passwordkeeper.data.repository.FirebaseAuthRepositoryImpl
import br.com.passwordkeeper.domain.usecase.LoginUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.koin.dsl.module

val firebaseModule = module {
    single<FirebaseAuth> { Firebase.auth }
}

val repositoryModule = module {
    single<FirebaseAuthRepositoryImpl> { FirebaseAuthRepositoryImpl(get<FirebaseAuth>()) }
}

val useCaseModule = module {
    single<LoginUseCase> { LoginUseCase(get<FirebaseAuthRepositoryImpl>()) }
}