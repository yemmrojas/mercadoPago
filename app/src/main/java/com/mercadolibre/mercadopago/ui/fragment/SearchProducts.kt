package com.mercadolibre.mercadopago.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import com.mercadolibre.mercadopago.R
import com.mercadolibre.mercadopago.databinding.FragmentSearchProductsBinding
import com.mercadolibre.mercadopago.domain.model.ProductsModel
import com.mercadolibre.mercadopago.presentation.state.State
import com.mercadolibre.mercadopago.presentation.viewModel.SearchProductsVM
import com.mercadolibre.mercadopago.ui.adapter.ListProductAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchProducts : Fragment() {


    private var _binding : FragmentSearchProductsBinding? = null
    private val  binding get() =  _binding!!
    private lateinit var listProductsAdapter: ListProductAdapter
    private val viewModel : SearchProductsVM by sharedViewModel()
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
        saveData()
    }

    private fun initRecycler(){
        listProductsAdapter = ListProductAdapter()
        binding.rvListProduct.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = listProductsAdapter
        }
        initObserver()
        onItemListener()
    }

    private fun initObserver(){
        onLoading(viewModel.loading)
        viewModel.products.observe(viewLifecycleOwner, {
            if (it.isNullOrEmpty()){
                listEmpty(viewModel.empty)
            }else{
                onLoading(viewModel.loading)
                listProductsAdapter.loadData(it)
            }
        })
    }

    private fun onLoading(b: Boolean) {
        if (b){
            binding.loading.visibility = View.VISIBLE
        }else{
            binding.loading.visibility = View.GONE
        }
    }

    private fun listEmpty(e : Boolean){
        if (e){
            binding.listEmpty.visibility = View.VISIBLE
        }else{
            binding.listEmpty.visibility = View.GONE
        }
    }

    private fun onSearch(){

        binding.search.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener,
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.loadData(searchTxt)
                initRecycler()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchTxt = newText ?: ""
                return false
            }
        })

    }

    private fun onItemListener(){
        listProductsAdapter.itemListener {
            viewModel.loadDataDescription(it)
            findNavController().navigate(R.id.action_searchProducts_to_descriptionProducts)
        }
    }

    private fun saveData(){
        if (viewModel.saveData == 1){
            initRecycler()
        }
    }


}