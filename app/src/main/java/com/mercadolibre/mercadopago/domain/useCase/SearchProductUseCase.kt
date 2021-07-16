package com.mercadolibre.mercadopago.domain.useCase

import com.mercadolibre.mercadopago.domain.repository.MercadoRepo

class SearchProductUseCase(private val mercadoRepo: MercadoRepo) {
    suspend fun get(string: String) = mercadoRepo.getProducts(string)
}