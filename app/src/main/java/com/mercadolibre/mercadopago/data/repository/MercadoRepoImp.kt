package com.mercadolibre.mercadopago.data.repository

import com.mercadolibre.mercadopago.common.Either
import com.mercadolibre.mercadopago.data.remote.MercadoApi
import com.mercadolibre.mercadopago.data.remote.model.toDomain
import com.mercadolibre.mercadopago.domain.model.ProductsModel
import com.mercadolibre.mercadopago.domain.repository.MercadoRepo
import java.lang.Exception

class MercadoRepoImp(private val mercadoApi: MercadoApi) : MercadoRepo {
    override  suspend fun getProducts(string: String): Either<Throwable, List<ProductsModel>> = try {
        Either.Right(mercadoApi.getProducts(string).toDomain())
    }catch (ex : Exception){
        Either.Left(ex)
    }
}