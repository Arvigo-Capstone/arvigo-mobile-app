package id.arvigo.arvigobasecore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import id.arvigo.arvigobasecore.data.repository.*
import id.arvigo.arvigobasecore.data.source.local.AuthPreferences
import id.arvigo.arvigobasecore.data.source.network.ApiService
import id.arvigo.arvigobasecore.domain.repository.AuthRepository
import id.arvigo.arvigobasecore.domain.usecase.LoginUseCase
import id.arvigo.arvigobasecore.ui.feature.brand.BrandViewModel
import id.arvigo.arvigobasecore.ui.feature.brand.brand_detail.BrandDetailViewModel
import id.arvigo.arvigobasecore.ui.feature.eyewear.EyewearViewModel
import id.arvigo.arvigobasecore.ui.feature.faceshape.FaceShapeViewModel
import id.arvigo.arvigobasecore.ui.feature.home.HomeViewModel
import id.arvigo.arvigobasecore.ui.feature.login.LoginViewModel
import id.arvigo.arvigobasecore.ui.feature.makeup.MakeupViewModel
import id.arvigo.arvigobasecore.ui.feature.offer_detail.OfferViewModel
import id.arvigo.arvigobasecore.ui.feature.personality.PersonalityViewModel
import id.arvigo.arvigobasecore.ui.feature.product_detail.ProductDetailViewModel
import id.arvigo.arvigobasecore.ui.feature.profile.ProfileRepository
import id.arvigo.arvigobasecore.ui.feature.profile.ProfileViewModel
import id.arvigo.arvigobasecore.ui.feature.profile.screen.ProfileEditViewModel
import id.arvigo.arvigobasecore.ui.feature.recommendation_store.RecommenStoreViewModel
import id.arvigo.arvigobasecore.ui.feature.register.RegisterViewModel
import id.arvigo.arvigobasecore.ui.feature.search.SearchViewModel
import id.arvigo.arvigobasecore.ui.feature.splash.SplashViewModel
import id.arvigo.arvigobasecore.ui.feature.stores.StoreViewModel
import id.arvigo.arvigobasecore.ui.feature.subscription.SubscriptionViewModel
import id.arvigo.arvigobasecore.ui.feature.wishlist.model.WishListViewModel
import id.arvigo.arvigobasecore.util.Constant.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.compose.BuildConfig
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        val loggingInterceptor =
            HttpLoggingInterceptor().apply {
                level =
                    if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            }
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(100, TimeUnit.SECONDS) // Set the connection timeout
            .readTimeout(30, TimeUnit.SECONDS) // Set the read timeout
            .writeTimeout(100, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val viewModelModules = module {
    viewModel { LoginViewModel(get(), get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { PersonalityViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { BrandViewModel(get()) }
    viewModel { EyewearViewModel(get())}
    viewModel { SplashViewModel(get()) }
    viewModel { ProfileViewModel(get(),get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { StoreViewModel(get()) }
    viewModel { MakeupViewModel(get()) }
    viewModel { WishListViewModel(get()) }
    viewModel { ProfileEditViewModel(get(),get()) }
    viewModel { ProductDetailViewModel(get(), get(), get(), get()) }
    viewModel { RecommenStoreViewModel(get()) }
    viewModel { BrandDetailViewModel(get()) }
    viewModel { FaceShapeViewModel(get()) }
    viewModel { OfferViewModel(get(), get(), get(), get())}
    viewModel { SubscriptionViewModel(get(), get()) }
}

val useCaseModule = module {
    single { LoginUseCase(get()) }
    single<AuthRepository> { DefaultAuthRepository() }
    single<PersonalityRepository> {
        PersonalityRepository(get(), get())
    }
    single<HomeProductRepository> {
        HomeProductRepository(get(), get())
    }
    single<BrandRepository> {
        BrandRepository(get(), get())
    }
    single<CategoryRepository> {
        CategoryRepository(get(), get())
    }
    single<SearchProductRepository> {
        SearchProductRepository(get(), get())
    }
    single<StoreRepository> {
        StoreRepository(get(), get())
    }
    single { WishListsRepository(get(),get()) }
    single { ProfileRepository(get(),get()) }
    single { ProductDetailRepository(get(),get()) }
    single { FaceshapeRepository(get(),get()) }
    single { OfferDetailRepository(get(),get()) }
}

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_key")

val dataPreferencesModule = module {

    single {
        AuthPreferences( dataStore = androidContext().dataStore)
    }
}

