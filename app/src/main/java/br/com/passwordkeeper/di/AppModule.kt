package br.com.passwordkeeper.di


import android.content.Context
import br.com.passwordkeeper.BuildConfig
import br.com.passwordkeeper.data.repository.*
import br.com.passwordkeeper.data.source.web.AdviceWebClient
import br.com.passwordkeeper.data.source.web.service.AdviceService
import br.com.passwordkeeper.domain.mapper.*
import br.com.passwordkeeper.domain.repository.AdviceRepository
import br.com.passwordkeeper.domain.repository.AuthRepository
import br.com.passwordkeeper.domain.repository.CardRepository
import br.com.passwordkeeper.domain.usecases.create_card.CreateCardUseCase
import br.com.passwordkeeper.domain.usecases.create_card.CreateCardUseCaseImpl
import br.com.passwordkeeper.domain.usecases.create_user.CreateUserUseCase
import br.com.passwordkeeper.domain.usecases.create_user.CreateUserUseCaseImpl
import br.com.passwordkeeper.domain.usecases.delete_card.DeleteCardUseCase
import br.com.passwordkeeper.domain.usecases.delete_card.DeleteCardUseCaseImpl
import br.com.passwordkeeper.domain.usecases.form_validation_create_card.FormValidationCreateCardUseCase
import br.com.passwordkeeper.domain.usecases.form_validation_create_card.FormValidationCreateCardUseCaseImpl
import br.com.passwordkeeper.domain.usecases.form_validation_sign_in.FormValidationSignInUseCase
import br.com.passwordkeeper.domain.usecases.form_validation_sign_in.FormValidationSignInUseCaseImpl
import br.com.passwordkeeper.domain.usecases.form_validation_sign_up.FormValidationSignUpUseCase
import br.com.passwordkeeper.domain.usecases.form_validation_sign_up.FormValidationSignUpUseCaseImpl
import br.com.passwordkeeper.domain.usecases.get_advice.GetAdviceUseCase
import br.com.passwordkeeper.domain.usecases.get_advice.GetAdviceUseCaseImpl
import br.com.passwordkeeper.domain.usecases.get_all_cards.GetAllCardsUseCase
import br.com.passwordkeeper.domain.usecases.get_all_cards.GetAllCardsUseCaseImpl
import br.com.passwordkeeper.domain.usecases.get_card_by_id.GetCardByIdUseCase
import br.com.passwordkeeper.domain.usecases.get_card_by_id.GetCardByIdUseCaseImpl
import br.com.passwordkeeper.domain.usecases.get_cards_by_category.GetCardsByCategoryUseCase
import br.com.passwordkeeper.domain.usecases.get_cards_by_category.GetCardsByCategoryUseCaseImpl
import br.com.passwordkeeper.domain.usecases.get_current_user.GetCurrentUserUseCase
import br.com.passwordkeeper.domain.usecases.get_current_user.GetCurrentUserUseCaseImpl
import br.com.passwordkeeper.domain.usecases.get_favorites_cards.GetFavoriteCardsUseCase
import br.com.passwordkeeper.domain.usecases.get_favorites_cards.GetFavoriteCardsUseCaseImpl
import br.com.passwordkeeper.domain.usecases.get_items_count_by_categories.GetItemsCountByCategoriesUseCase
import br.com.passwordkeeper.domain.usecases.get_items_count_by_categories.GetItemsCountByCategoriesUseCaseImpl
import br.com.passwordkeeper.domain.usecases.password_validation.PasswordValidationUseCase
import br.com.passwordkeeper.domain.usecases.password_validation.PasswordValidationUseCaseImpl
import br.com.passwordkeeper.domain.usecases.sign_in.SignInUseCase
import br.com.passwordkeeper.domain.usecases.sign_in.SignInUseCaseImpl
import br.com.passwordkeeper.domain.usecases.sign_out.SignOutUseCase
import br.com.passwordkeeper.domain.usecases.sign_out.SignOutUseCaseImpl
import br.com.passwordkeeper.domain.usecases.sort_cardview_list.SortCardViewListUseCase
import br.com.passwordkeeper.domain.usecases.sort_cardview_list.SortCardViewListUseCaseImpl
import br.com.passwordkeeper.domain.usecases.update_card.UpdateCardUseCase
import br.com.passwordkeeper.domain.usecases.update_card.UpdateCardUseCaseImpl
import br.com.passwordkeeper.presentation.features.MainViewModel
import br.com.passwordkeeper.presentation.features.create_card.CreateNewCardViewModel
import br.com.passwordkeeper.presentation.features.home.HomeViewModel
import br.com.passwordkeeper.presentation.features.list_cards.ListCardsViewModel
import br.com.passwordkeeper.presentation.features.sign_in.SignInViewModel
import br.com.passwordkeeper.presentation.features.sign_up.SignUpViewModel
import br.com.passwordkeeper.presentation.features.list_cards.ListCardsAdapter
import br.com.passwordkeeper.presentation.features.home.CategoryAdapter
import br.com.passwordkeeper.presentation.features.home.FavoriteAdapter
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

val mappersModule = module {
    single<AdviceDataMapper> { AdviceDataMapper() }
    single<AdviceDomainMapper> { AdviceDomainMapper() }
    single<CardFirestoreMapper> { CardFirestoreMapper() }
    single<CardDataMapper> { CardDataMapper() }
    single<CardDomainMapper> { CardDomainMapper() }
    single<CategoryDomainMapper> { CategoryDomainMapper() }
    single<UserFirestoreMapper> { UserFirestoreMapper() }
    single<UserDataMapper> { UserDataMapper() }
    single<UserDomainMapper> { UserDomainMapper() }
}

val repositoryModule = module {
    single<AuthRepository> {
        FirebaseAuthRepositoryImpl(
            get<FirebaseAuth>(),
            get<FirebaseFirestore>(),
            get<UserFirestoreMapper>(),
            get<UserDataMapper>()
        )
    }
    single<AdviceRepository> {
        AdviceRepositoryImpl(get<AdviceWebClient>(), get<AdviceDataMapper>())
    }
    single<CardRepository> {
        FirebaseCardRepositoryImpl(
            get<FirebaseFirestore>(),
            get<CardFirestoreMapper>(),
            get<CardDataMapper>()
        )
    }
}

val useCaseModule = module {
    single<SignInUseCase> { SignInUseCaseImpl(get<AuthRepository>(), get<UserDomainMapper>()) }
    single<GetAdviceUseCase> { GetAdviceUseCaseImpl(get<AdviceRepository>(), get<AdviceDomainMapper>()) }
    single<PasswordValidationUseCase> { PasswordValidationUseCaseImpl() }
    single<FormValidationSignInUseCase> { FormValidationSignInUseCaseImpl() }
    single<FormValidationSignUpUseCase> {
        FormValidationSignUpUseCaseImpl(get<PasswordValidationUseCase>())
    }
    single<GetCardByIdUseCase> {
        GetCardByIdUseCaseImpl(
            get<CardRepository>(),
            get<CardDomainMapper>()
        )
    }
    single<GetAllCardsUseCase> {
        GetAllCardsUseCaseImpl(
            get<CardRepository>(),
            get<CardDomainMapper>()
        )
    }
    single<CreateCardUseCase> {
        CreateCardUseCaseImpl(
            get<CardRepository>()
        )
    }
    single<UpdateCardUseCase> {
        UpdateCardUseCaseImpl(
            get<CardRepository>()
        )
    }
    single<DeleteCardUseCase> {
        DeleteCardUseCaseImpl(
            get<CardRepository>()
        )
    }
    single<GetFavoriteCardsUseCase> {
        GetFavoriteCardsUseCaseImpl(
            get<CardRepository>(),
            get<CardDomainMapper>()
        )
    }
    single<GetItemsCountByCategoriesUseCase> {
        GetItemsCountByCategoriesUseCaseImpl(
            get<CardRepository>(),
            get<CategoryDomainMapper>()
        )
    }
    single<GetCardsByCategoryUseCase> {
        GetCardsByCategoryUseCaseImpl(
            get<CardRepository>(),
            get<CardDomainMapper>()
        )
    }
    single<FormValidationCreateCardUseCase> {
        FormValidationCreateCardUseCaseImpl(
            get<Context>()
        )
    }
    single<CreateUserUseCase> {
        CreateUserUseCaseImpl(get<AuthRepository>())
    }
    single<SignOutUseCase> {
        SignOutUseCaseImpl(get<AuthRepository>())
    }
    single<GetCurrentUserUseCase> {
        GetCurrentUserUseCaseImpl(get<AuthRepository>(),get<UserDomainMapper>())
    }
    single<SortCardViewListUseCase> { SortCardViewListUseCaseImpl(get<Context>()) }
}

val viewModelModule = module {
    viewModel<HomeViewModel> {
        HomeViewModel(
            get<GetAdviceUseCase>(),
            get<GetFavoriteCardsUseCase>(),
            get<GetItemsCountByCategoriesUseCase>(),
            get<SortCardViewListUseCase>()
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
            get<CreateUserUseCase>(),
            get<FormValidationSignUpUseCase>(),
            get<PasswordValidationUseCase>()
        )
    }
    viewModel<MainViewModel> {
        MainViewModel(get<GetCurrentUserUseCase>())
    }
    viewModel<CreateNewCardViewModel> {
        CreateNewCardViewModel(
            get<CreateCardUseCase>(),
            get<FormValidationCreateCardUseCase>()
        )
    }
    viewModel<ListCardsViewModel> {
        ListCardsViewModel(
            get<GetAllCardsUseCase>(),
            get<GetCardsByCategoryUseCase>(),
            get<SortCardViewListUseCase>()
        )
    }
}

val recyclerViewAdaptersModule = module {
    factory<CategoryAdapter> { CategoryAdapter(get<Context>()) }
    factory<FavoriteAdapter> { FavoriteAdapter(get<Context>()) }
    factory<ListCardsAdapter> { ListCardsAdapter(get<Context>()) }
}