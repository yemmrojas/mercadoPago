package com.mercadolibre.mercadopago.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.mercadolibre.mercadopago.databinding.ActivityMainBinding
import com.mercadolibre.mercadopago.presentation.state.State
import com.mercadolibre.mercadopago.presentation.viewModel.SearchProductsVM
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    companion object{
        fun startActivity(context: Context){
            context.startActivity(Intent(context, HomeActivity::class.java))
        }
    }

    private val viewModel : SearchProductsVM by viewModel()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeState()
    }

    private fun observeState(){
        viewModel.state.observe(this, { isState->
            when(isState){
                is State.Empty -> {
                    viewModel.loading = false
                    viewModel.empty = true
                }
                is State.Failed -> {
                    viewModel.loading = false
                    Log.e("falloProductos", isState.failure.message.toString())
                }
                is State.Loading -> {
                    viewModel.loading = true
                }
                is State.Success -> {
                    viewModel.loading = false
                    viewModel.saveData = 1
                    viewModel.products.value = isState.responseTo()
                }
            }
        })
    }
}