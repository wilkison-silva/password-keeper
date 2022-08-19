package br.com.passwordkeeper.di


import android.content.Context
import br.com.passwordkeeper.BuildConfig
import br.com.passwordkeeper.data.repository.*
import br.com.passwordkeeper.data.source.web.AdviceWebClient
import br.com.passwordkeeper.data.source.web.service.AdviceService
import br.com.passwordkeeper.domain.usecase.*
import br.com.passwordkeeper.presentation.ui.recyclerview.adapter.CategoryAdapter
import br.com.passwordkeeper.presentation.ui.recyclerview.adapter.FavoriteAdapter
import br.com.passwordkeeper.presentation.ui.viewmodel.HomeViewModel
import br.com.passwordkeeper.presentation.ui.viewmodel.MainViewModel
import br.com.passwordkeeper.presentation.ui.viewmodel.SignInViewModel
import br.com.passwordkeeper.presentation.ui.viewmodel.SignUpViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
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
    single<FirebaseStorage> { Firebase.storage }
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
    single<FormValidationSignUpUseCase> {
        FormValidationSignUpUseCaseImpl(get<PasswordValidationUseCase>())
    }
    single<CardUseCase> { FirebaseCardUseCaseImpl(get<CardRepository>()) }
}

val viewModelModule = module {
    viewModel<HomeViewModel> {
        HomeViewModel(
            get<AdviceUseCase>(),
            get<SignInUseCase>(),
            get<CardUseCase>()
        )
    }
    viewModel<SignInViewModel> {
        SignInViewModel(
            get<SignInUseCase>(),
            get<FormValidationSignInUseCase>()
        )
    }
    viewModel<SignUpViewModel> {
        SignUpViewModel(
            get<SignUpUseCase>(),
            get<FormValidationSignUpUseCase>(),
            get<PasswordValidationUseCase>()
        )
    }
    viewModel<MainViewModel> { MainViewModel() }
}

val recyclerViewAdaptersModule = module {
    factory<CategoryAdapter> { CategoryAdapter(get<Context>()) }
    factory<FavoriteAdapter> { FavoriteAdapter() }
}