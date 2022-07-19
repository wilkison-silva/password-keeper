package br.com.passwordkeeper.di

import br.com.passwordkeeper.BuildConfig
import br.com.passwordkeeper.data.repository.AdviceRepository
import br.com.passwordkeeper.data.repository.AdviceRepositoryImpl
import br.com.passwordkeeper.data.repository.AuthRepository
import br.com.passwordkeeper.data.repository.FirebaseAuthRepositoryImpl
import br.com.passwordkeeper.data.source.web.AdviceWebClient
import br.com.passwordkeeper.data.source.web.service.AdviceService
import br.com.passwordkeeper.domain.usecase.AdviceUseCase
import br.com.passwordkeeper.domain.usecase.AdviceUseCaseImpl
import br.com.passwordkeeper.domain.usecase.LoginUseCase
import br.com.passwordkeeper.domain.usecase.LoginUseCaseImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.logging.Level

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
}

val serviceModule = module {
    single<AdviceService> { get<Retrofit>().create(AdviceService::class.java) }
}

val webClientModule = module {
    single<AdviceWebClient> { AdviceWebClient(get<AdviceService>()) }
}

val repositoryModule = module {
    single<AuthRepository> { FirebaseAuthRepositoryImpl(get<FirebaseAuth>()) }
    single<AdviceRepository> { AdviceRepositoryImpl(get<AdviceWebClient>()) }
}

val useCaseModule = module {
    single<LoginUseCase> { LoginUseCaseImpl(get<AuthRepository>()) }
    single<AdviceUseCase> { AdviceUseCaseImpl(get<AdviceRepository>()) }
}