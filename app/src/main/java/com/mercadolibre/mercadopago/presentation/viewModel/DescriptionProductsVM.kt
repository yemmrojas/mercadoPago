package com.mercadolibre.mercadopago.presentation.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mercadolibre.mercadopago.domain.model.ProductsModel

class DescriptionProductsVM : ViewModel() {
    val product = MutableLiveData<ProductsModel>()

    fun loadDataDescription(product : ProductsModel){
        this.product.value = product
    }
}