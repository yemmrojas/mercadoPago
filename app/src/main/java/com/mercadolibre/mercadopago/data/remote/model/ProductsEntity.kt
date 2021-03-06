package com.mercadolibre.mercadopago.data.remote.model

import com.google.gson.annotations.SerializedName
import com.mercadolibre.mercadopago.domain.model.ProductsModel

data class ProductsEntity (
    @SerializedName("site_id")
    val site_id : String = "",
    @SerializedName("query")
    val query : String = "",
    @SerializedName("results")
    val results : List<Results> = ArrayList()
){
    data class Results(
        @SerializedName("id")
        val id : String = "",
        @SerializedName("title")
        val title : String = "",
        @SerializedName("thumbnail")
        val thumbnail : String = "",
        @SerializedName("price")
        val price : Double = 0.0
    )
}

fun ProductsEntity.toDomain() = results.map {
    ProductsModel(
        title = it.title,
        prace = it.price,
        image = it.thumbnail
    )
}