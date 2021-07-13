package com.mercadolibre.mercadopago.data.remote

import com.mercadolibre.mercadopago.data.remote.model.ProductsEntity
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Part
import retrofit2.http.Query

interface MercadoApi {

    @GET("sites/MLA/search")
    suspend fun getProducts(@Query("q") txt : String) : ProductsEntity
}