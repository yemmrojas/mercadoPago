package com.mercadolibre.mercadopago.data.remote

import com.mercadolibre.mercadopago.data.remote.model.ProductsEntity
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Part

interface MercadoApi {

    @GET("sites/MLA/search{txt}")
    suspend fun getProducts(@Part("txt") txt : String) : ProductsEntity
}