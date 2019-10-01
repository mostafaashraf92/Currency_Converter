package com.revolut.currency.di

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.revolut.currency.networking.ApiService
import com.revolut.currency.networking.RatesService
import com.revolut.currency.ui.rates.model.RatesResponse
import com.revolut.currency.ui.rates.view.RatesAdapter
import com.revolut.currency.ui.rates.viewmodel.CurrencyViewModel
import com.revolut.currency.utils.CurrencyUtils
import com.revolut.currency.utils.CustomDeserializer
import com.revolut.currency.utils.RatesListsUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val appModule = module {
    factory {
        RatesAdapter()
    }
    single { CurrencyUtils() }
    factory { RatesService(get()) }
    single { RatesListsUtils(get()) }
}
val networkModule = module {
    single { provideRetrofit().create(ApiService::class.java) }
}

val viewModelModule = module {
    viewModel { CurrencyViewModel(get(), CoroutineScope(Dispatchers.Main)) }
}



fun provideRetrofit(): Retrofit {
    val gson = GsonBuilder()
        .registerTypeAdapter(RatesResponse::class.java, CustomDeserializer()).create()
    return Retrofit.Builder()
        .baseUrl("https://revolut.duckdns.org/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
}
