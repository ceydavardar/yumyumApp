package com.ceydavardar.yumyumapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ceydavardar.yumyumapp.R
import com.ceydavardar.yumyumapp.data.entity.WholeCartFoods
import com.ceydavardar.yumyumapp.databinding.FragmentCartBinding
import com.ceydavardar.yumyumapp.ui.adapter.CartFragmentAdapter
import com.ceydavardar.yumyumapp.ui.viewmodel.CartViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment: Fragment() {

    private lateinit var binding: FragmentCartBinding
    private lateinit var viewModel: CartViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)

        binding.home.setOnClickListener {
            val gecis = CartFragmentDirections.actionCartFragmentToMainFragment()
            Navigation.findNavController(it).navigate(gecis)
        }

        binding.backButton.setOnClickListener {
            val gecis = CartFragmentDirections.actionCartFragmentToMainFragment()
            Navigation.findNavController(it).navigate(gecis)
        }

        viewModel.data.observe(viewLifecycleOwner) {

            if (it != null) {
                if (it.isNotEmpty()) {
                    var adapter = CartFragmentAdapter(requireContext(), it.toMutableList(), viewModel)
                    binding.cartRV.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
                    binding.cartRV.adapter = adapter
                    return@observe
                }
            }

            binding.cartRV.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
            var adapter = CartFragmentAdapter(requireContext(), mutableListOf(), viewModel)
            binding.cartRV.adapter = adapter

        }

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: CartViewModel by viewModels()
        viewModel = tempViewModel
    }

}