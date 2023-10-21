package com.ceydavardar.yumyumapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ceydavardar.yumyumapp.data.entity.CartFood
import com.ceydavardar.yumyumapp.data.entity.WholeCartFoods
import com.ceydavardar.yumyumapp.databinding.CartCardDesignBinding
import com.ceydavardar.yumyumapp.ui.viewmodel.CartViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartFragmentAdapter(
    var mContext: Context,
    var cartFoodList: MutableList<WholeCartFoods>,
    var viewModel: CartViewModel
): RecyclerView.Adapter<CartFragmentAdapter.ViewHolder>() {

    inner class ViewHolder(val design: CartCardDesignBinding): RecyclerView.ViewHolder(design.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CartCardDesignBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return cartFoodList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val cartFood = cartFoodList[position]
        val design = holder.design

        val first = cartFood.cartFoodList.first()

        Glide.with(mContext).load("http://kasimadalan.pe.hu/yemekler/resimler/${first.yemek_resim_adi}").into(design.cartFoodImage)

        design.cartFoodName.text = cartFood.foodName
        design.foodPrice.text = "₺${first.yemek_fiyat}"
        design.foodAmount.text = cartFood.totalAmount.toString()
        design.totalPriceTextView.text = "₺${cartFood.totalPrice}"

        design.delete.setOnClickListener {
            if (cartFood.cartFoodList.isEmpty()) {
                viewModel.data.value = emptyList()
            } else {
                CoroutineScope(Dispatchers.Main).launch {
                    if (viewModel.removeFoodFromCart(cartFood)) {
                        viewModel.loadCartFoods(viewModel.userRepo.username!!) {}
                    }
                }
            }
        }

    }

}