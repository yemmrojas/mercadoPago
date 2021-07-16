package com.mercadolibre.mercadopago.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mercadolibre.mercadopago.databinding.HolderListProductsBinding
import com.mercadolibre.mercadopago.domain.model.ProductsModel
import com.mercadolibre.mercadopago.util.CallbackT
import com.mercadolibre.mercadopago.util.toPrice

class ListProductAdapter : RecyclerView.Adapter<ListProductAdapter.Holder>() {

    private val listProduct = ArrayList<ProductsModel>()
    private lateinit var callback : CallbackT<ProductsModel>

    class Holder(val binding : HolderListProductsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder =
        Holder(HolderListProductsBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.binding.title.text = listProduct[position].title
        holder.binding.price.text = toPrice(listProduct[position].prace.toString())
        val url = listProduct[position].image
        Glide
            .with(holder.itemView.context)
            .load(url)
            .centerCrop()
            .into(holder.binding.img)

        holder.binding.btnDeta.setOnClickListener {
            callback.invoke(listProduct[position])
        }
    }

    override fun getItemCount(): Int = listProduct.size

    fun itemListener(callbackT: CallbackT<ProductsModel>){
        this.callback = callbackT
    }

    fun loadData (data : List<ProductsModel>){
        listProduct.clear()
        listProduct.addAll(data)
        notifyDataSetChanged()
    }
}