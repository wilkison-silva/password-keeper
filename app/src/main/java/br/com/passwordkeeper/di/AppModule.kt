package br.com.passwordkeeper.di

import br.com.passwordkeeper.BuildConfig
import br.com.passwordkeeper.data.repository.*
import br.com.passwordkeeper.data.source.web.AdviceWebClient
import br.com.passwordkeeper.data.source.web.service.AdviceService
import br.com.passwordkeeper.domain.usecase.*
import br.com.passwordkeeper.presentation.ui.viewModel.HomeViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val URL_BASE = "https://api.adviceslip.com"

val retrofitModule = module {
    single<OkHttpClient> {
        val client = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
            client.addInterceptor(logging)
        }
        client.build()
    }
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(URL_BASE)
            .client(get<OkHttpClient>())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
}

val firebaseModule = module {
    single<FirebaseAuth> { Firebase.auth }
    single<FirebaseFirestore> { Firebase.firestore }
}

val serviceModule = module {
    single<AdviceService> { get<Retrofit>().create(AdviceService::class.java) }
}

val webClientModule = module {
    single<AdviceWebClient> { AdviceWebClient(get<AdviceService>()) }
}

val repositoryModule = module {
    single<AuthRepository> {
        FirebaseAuthRepositoryImpl(
            get<FirebaseAuth>(),
            get<FirebaseFirestore>()
        )
    }
    single<AdviceRepository> { AdviceRepositoryImpl(get<AdviceWebClient>()) }
    single<CardRepository> { FirebaseCardRepositoryImpl(get<FirebaseFirestore>()) }
}

val useCaseModule = module {
    single<SignInUseCase> { SignInUseCaseImpl(get<AuthRepository>()) }
    single<SignUpUseCase> { SignUpUseCaseImpl(get<AuthRepository>()) }
    single<AdviceUseCase> { AdviceUseCaseImpl(get<AdviceRepository>()) }
    single<PasswordValidationUseCase> { PasswordValidationUseCaseImpl() }
    single<FormValidationSignInUseCase> { FormValidationSignInUseCaseImpl() }
    single<FormValidationSignUpUseCase> { FormValidationSignUpUseCaseImpl(get<PasswordValidationUseCase>()) }

}

val viewModelModule = module {
    viewModel<HomeViewModel> { HomeViewModel(get<AdviceUseCase>()) }
}