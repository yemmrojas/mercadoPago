package com.mercadolibre.mercadopago.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mercadolibre.mercadopago.domain.model.ProductsModel
import com.mercadolibre.mercadopago.domain.useCase.SearchProductUseCase
import com.mercadolibre.mercadopago.presentation.state.State
import kotlinx.coroutines.launch

class SearchProductsVM(val searchProductUseCase: SearchProductUseCase) : ViewModel() {

    private val _state =  MutableLiveData<State>()
    val state : LiveData<State> get() = _state
    val products = MutableLiveData<List<ProductsModel>>()
    private val _product = MutableLiveData<ProductsModel>()
    val product : LiveData<ProductsModel> get() = _product
    var loading = false
    var empty = false
    var saveData = 0

    fun loadData(txtSearch : String){
        _state.value = State.Loading
        viewModelScope.launch {
            searchProductUseCase.get(txtSearch).either(
                ::faildData,
                ::sucessData
            )
        }
    }

    private fun faildData(failure : Throwable){
        _state.value = State.Failed(failure)
    }

    private fun sucessData(products : List<ProductsModel>){
        if (products.isNullOrEmpty()) _state.value = State.Empty
        else _state.value = State.Success(products)
    }

    fun loadDataDescription(product : ProductsModel){
        this._product.value = product
    }

}