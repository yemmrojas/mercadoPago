package com.mercadolibre.mercadopago.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.mercadolibre.mercadopago.databinding.FragmentSearchProductsBinding
import com.mercadolibre.mercadopago.domain.model.ProductsModel
import com.mercadolibre.mercadopago.presentation.state.State
import com.mercadolibre.mercadopago.presentation.viewModel.SearchProductsVM
import com.mercadolibre.mercadopago.ui.adapter.ListProductAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchProducts : Fragment() {


    private var _binding : FragmentSearchProductsBinding? = null
    private val  binding get() =  _binding!!
    private var listProducts = ArrayList<ProductsModel>()
    private lateinit var listProductsAdapter: ListProductAdapter
    private val viewModel : SearchProductsVM by viewModel()
    private var searchTxt: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initView() {
        onSearch()
    }

    private fun initRecycler(){
        listProductsAdapter = ListProductAdapter()
        binding.rvListProduct.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = listProductsAdapter
        }

    }

    private fun observeState(){
        viewModel.state.observe(viewLifecycleOwner, { isState->
            when(isState){
                is State.Empty -> binding.listEmpty.visibility = View.VISIBLE
                is State.Failed -> {
                    onLoading(false)
                    onFailed("Fallo al querer traer la lista de productos")
                }
                is State.Loading -> onLoading(true)
                is State.Success -> {
                    onLoading(false)
                    listProducts = isState.responseTo()
                    listProductsAdapter.loadData(listProducts)
                }
            }
        })
    }

    private fun onFailed(s: String) {
        Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show()
    }

    private fun onLoading(b: Boolean) {
        if (b){
            binding.loading.visibility = View.VISIBLE
        }else{
            binding.loading.visibility = View.GONE
        }
    }

    private fun onSearch(){

        binding.search.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener,
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.loadData(searchTxt)
                observeState()
                initRecycler()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchTxt = newText ?: ""
                return false
            }
        })

    }


}