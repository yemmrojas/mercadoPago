package com.mercadolibre.mercadopago.di

import com.mercadolibre.mercadopago.BuildConfig
import com.mercadolibre.mercadopago.BuildConfig.BASE_URL
import com.mercadolibre.mercadopago.data.remote.MercadoApi
import com.mercadolibre.mercadopago.data.repository.MercadoRepoImp
import com.mercadolibre.mercadopago.domain.repository.MercadoRepo
import com.mercadolibre.mercadopago.domain.useCase.SearchProductUseCase
import com.mercadolibre.mercadopago.network.createNetworkClient
import com.mercadolibre.mercadopago.presentation.viewModel.SearchProductsVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

private const val RETROIT_NAME = "MercadoApi"

// injection presentation
val viewModelModule : Module = module {
    viewModel {
        SearchProductsVM(searchProductUseCase = get())
    }

}

// injection domain
val useCaseModule : Module = module {
    factory { SearchProductUseCase(mercadoRepo = get()) }

}

// injection data
val repositoryModule : Module = module {
    single {
        MercadoRepoImp(mercadoApi = get()) as MercadoRepo
    }

}

// injection network

val networkModule : Module = module {
    single(named(RETROIT_NAME)) {
        createNetworkClient(
            baseUrl = BASE_URL,
            debug = BuildConfig.DEBUG
        )
    }

    single { get<Retrofit>(named(RETROIT_NAME)).create(MercadoApi::class.java) }

}

