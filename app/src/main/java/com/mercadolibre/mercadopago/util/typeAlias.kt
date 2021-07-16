package com.mercadolibre.mercadopago.util

typealias CallbackT<T> = ((T) -> Unit)
typealias CallbackTR<T,R> = ((T,R) -> Unit)