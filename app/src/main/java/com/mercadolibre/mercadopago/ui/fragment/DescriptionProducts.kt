package com.mercadolibre.mercadopago.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.mercadolibre.mercadopago.databinding.FragmentDescriptionProductsBinding
import com.mercadolibre.mercadopago.domain.model.ProductsModel
import com.mercadolibre.mercadopago.presentation.viewModel.DescriptionProductsVM
import com.mercadolibre.mercadopago.util.toPrice

class DescriptionProducts : Fragment() {

    private  var _binding : FragmentDescriptionProductsBinding? = null
    private val binding get() = _binding!!
    private val viewModel : DescriptionProductsVM
    get() = ViewModelProvider(this)[DescriptionProductsVM::class.java]

    private lateinit var product : ProductsModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDescriptionProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadDetalle()
        onBack()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun loadDetalle(){
        val args = arguments?.getString("product")
        args.let {
            product = Gson().fromJson(it, ProductsModel::class.java)
        }
        viewModel.loadDataDescription(product)
        observerData()
    }

    private fun observerData(){
        viewModel.product.observe(viewLifecycleOwner, {
            Glide.with(requireActivity())
                .load(it.image)
                .centerCrop()
                .into(binding.imgProduct)

            binding.precio.text = toPrice(it.prace.toString())
            binding.title.text = it.title
        })
    }

    private fun onBack(){
        binding.imgBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

}