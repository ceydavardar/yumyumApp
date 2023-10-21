package com.ceydavardar.yumyumapp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.ceydavardar.yumyumapp.R
import com.ceydavardar.yumyumapp.data.entity.Food
import com.ceydavardar.yumyumapp.databinding.FragmentMainBinding
import com.ceydavardar.yumyumapp.ui.adapter.MainFragmentAdapter
import com.ceydavardar.yumyumapp.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment: Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        val glide = Glide.with(this)

        viewModel.foodList.observe(viewLifecycleOwner) {
            binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            val adapter = MainFragmentAdapter(requireContext(), it, glide)
            binding.recyclerView.adapter = adapter
        }

        binding.cartImageView.setOnClickListener {
            val gecis = MainFragmentDirections.actionMainFragmentToCartFragment()
            Navigation.findNavController(it).navigate(gecis)
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(kelime: String?): Boolean {
                if (kelime.isNullOrBlank()) {
                    viewModel.loadFoods {}
                } else {
                    val newList = mutableListOf<Food>()
                    val oldList = viewModel.foodList.value

                    if (oldList == null) {
                        viewModel.foodList.value = emptyList()
                        return true
                    }

                    oldList.forEach {
                        if (it.yemekAdi.contains(kelime, true)) {
                            newList.add(it)
                        }
                    }

                    viewModel.foodList.value = newList

                }
                return true
            }

            override fun onQueryTextChange(kelime: String?): Boolean {
                if (kelime.isNullOrBlank()) {
                    viewModel.loadFoods {}
                } else {
                    val newList = mutableListOf<Food>()
                    val oldList = viewModel.foodList.value

                    if (oldList == null) {
                        viewModel.foodList.value = emptyList()
                        return false
                    }

                    oldList.forEach {
                        if (it.yemekAdi.contains(kelime, true)) {
                            newList.add(it)
                        }
                    }

                    viewModel.foodList.value = newList

                }
                return false
            }

        })

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: MainViewModel by viewModels()
        viewModel = tempViewModel
    }

}