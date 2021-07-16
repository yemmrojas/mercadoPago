package com.mercadolibre.mercadopago.domain.repository

import com.mercadolibre.mercadopago.common.Either
import com.mercadolibre.mercadopago.domain.model.ProductsModel

interface MercadoRepo {
     suspend fun getProducts(string: String) : Either<Throwable, List<ProductsModel>>
}