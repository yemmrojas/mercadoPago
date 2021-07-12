package com.mercadolibre.mercadopago.app

import android.app.Application
import com.mercadolibre.mercadopago.di.networkModule
import com.mercadolibre.mercadopago.di.repositoryModule
import com.mercadolibre.mercadopago.di.useCaseModule
import com.mercadolibre.mercadopago.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AppMercado : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin(){
        startKoin {
            // use Koin logger
            androidLogger()
            androidContext(this@AppMercado)
            modules(
                arrayListOf(
                    viewModelModule,
                    useCaseModule,
                    repositoryModule,
                    networkModule
                )
            )
        }
    }
}