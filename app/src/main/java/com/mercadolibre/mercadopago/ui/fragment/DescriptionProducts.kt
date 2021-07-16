package com.mercadolibre.mercadopago.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.mercadolibre.mercadopago.databinding.FragmentDescriptionProductsBinding
import com.mercadolibre.mercadopago.presentation.viewModel.SearchProductsVM
import com.mercadolibre.mercadopago.util.toPrice
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class DescriptionProducts : Fragment() {

    private  var _binding : FragmentDescriptionProductsBinding? = null
    private val binding get() = _binding!!

    private val viewModel : SearchProductsVM by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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