package com.ceydavardar.yumyumapp.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.ceydavardar.yumyumapp.data.entity.Food
import com.ceydavardar.yumyumapp.databinding.MainCardDesignBinding
import com.ceydavardar.yumyumapp.ui.fragment.MainFragmentDirections

class MainFragmentAdapter(
    var context: Context,
    var foodList: List<Food>,
    var glide: RequestManager
): RecyclerView.Adapter<MainFragmentAdapter.ViewHolder>() {

    inner class ViewHolder(val design: MainCardDesignBinding): RecyclerView.ViewHolder(design.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val design = MainCardDesignBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(design)
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val food = foodList[position]

        holder.design.yemekAdi.text = food.yemekAdi
        holder.design.yemekFiyat.text = "₺${food.yemekFiyat}"

        glide.load("http://kasimadalan.pe.hu/yemekler/resimler/${food.yemekResimAdi}").into(holder.design.yemekResim)

        holder.design.yemekResim.setOnClickListener {
            val gecis = MainFragmentDirections.actionMainFragmentToDetailFragment(food)
            Navigation.findNavController(it).navigate(gecis)
        }
    }

}