package com.ceydavardar.yumyumapp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.ceydavardar.yumyumapp.databinding.FragmentDetayBinding
import com.ceydavardar.yumyumapp.ui.viewmodel.DetailViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetayBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetayBinding.inflate(inflater, container, false)

        val bundle: DetailFragmentArgs by navArgs()

        val food = bundle.food

        Glide.with(this).load("http://kasimadalan.pe.hu/yemekler/resimler/${food.yemekResimAdi}").into(binding.detailFoodImage)

        binding.detailFoodName.text = food.yemekAdi

        binding.foodDetailPrice.text = "₺${food.yemekFiyat}"

        binding.addToCartButton.text = "Sepete Ekle (₺${binding.foodDetailPrice.text.toString()})"

        viewModel.amount.observe(viewLifecycleOwner) {
            binding.detailFoodAmount.text = it.toString()
            val total = food.yemekFiyat * it
            binding.addToCartButton.text = "Sepete Ekle (₺$total)"
        }

        binding.backButton.setOnClickListener {
            val gecis = DetailFragmentDirections.actionDetailFragmentToMainFragment()
            Navigation.findNavController(it).navigate(gecis)
        }

        binding.addCardView.setOnClickListener {
            viewModel.addTrigger()
        }

        binding.removeCardView.setOnClickListener {
            viewModel.removeTrigger()
        }

        binding.addToCartButton.setOnClickListener {
            if (viewModel.amount.value == null) {
                Snackbar.make(it, "Önce ürün eklemelisin.", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (viewModel.amount.value!! <= 0) {
                Snackbar.make(it, "Önce ürün eklemelisin.", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.addFoodToCart(food, viewModel.amount.value!!) { completed ->
                if (completed) {
                    Log.i("Sepet", "Sepete ${food.yemekAdi}, x${viewModel.amount.value!!} kadar eklendi.")
                    Snackbar.make(it, "Sepetine ${viewModel.amount.value!!} adet ${food.yemekAdi} eklendi.", Snackbar.LENGTH_SHORT).show()
                    val gecis = DetailFragmentDirections.actionDetailFragmentToMainFragment()
                    Navigation.findNavController(it).navigate(gecis)
                } else {
                    Snackbar.make(it, "Bir hata oluştu, lütfen daha sonra tekrar denyin.", Snackbar.LENGTH_SHORT).show()
                }
            }
        }

        return binding.root

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: DetailViewModel by viewModels()
        viewModel = tempViewModel
    }

}