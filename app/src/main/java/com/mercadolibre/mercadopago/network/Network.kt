package com.mercadolibre.mercadopago.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val TIME_OUT = 8L

fun createNetworkClient(baseUrl : String, debug : Boolean = false) : Retrofit =
    retrofitClient(baseUrl, httpCliet(debug))

private fun httpCliet(debug: Boolean) : OkHttpClient{
    val httpLogginInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
    val clientBuilder = OkHttpClient.Builder()

    if (debug){
        httpLogginInterceptor.level = HttpLoggingInterceptor.Level.BODY
        clientBuilder.run {
            addInterceptor(httpLogginInterceptor)
        }
    }

    clientBuilder.run {
        connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        readTimeout(TIME_OUT, TimeUnit.SECONDS).build()
    }

    return clientBuilder.build()

}

private fun retrofitClient(baseUrl: String, httpClient: OkHttpClient) =
    Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
