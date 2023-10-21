package com.ceydavardar.yumyumapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ceydavardar.yumyumapp.data.entity.Food
import com.ceydavardar.yumyumapp.data.repo.FoodsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    var repo: FoodsRepository
): ViewModel() {

    var foodList = MutableLiveData<List<Food>>()

    init {

        loadFoods {
            if (it) {
                Log.i("Yumyum", "Food list is loaded.")
            } else {
                Log.e("Yumyum", "Could not load the food list.")
            }
        }

    }

    fun loadFoods(callback: (Boolean) -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            Log.e("LoadFoods", "1")
            try {
                val response = repo.getFoods()

                if (response.success == 0) {
                    callback(false)
                    foodList.value = emptyList()
                    return@launch
                }
                val foods = response.yemekler

                val newFoodList = mutableListOf<Food>()
                for (res in foods) {
                    newFoodList.add(Food(res.yemek_id.toInt(), res.yemek_adi, res.yemek_resim_adi, res.yemek_fiyat.toInt()))
                }
                foodList.value = newFoodList
                callback(true)
            } catch (e: Exception) {
                Log.e("Yumyum", "Exception message${e.message}")
                callback(false)
                foodList.value = emptyList()
            }
        }
    }

//    fun addFoodToCart(food: Food, amount: Int, username: String, callback: (Boolean) -> Unit) {
//        CoroutineScope(Dispatchers.Main).launch {
//            try {
//                val response = repo.addFoodToCart(
//                    food.yemekAdi,
//                    food.yemekResimAdi,
//                    food.yemekFiyat,
//                    amount,
//                    username
//                )
//                if (response.success == 0) {
//                    callback(false)
//                } else {
//                    callback(true)
//                }
//            } catch (e: Exception) {
//                callback(false)
//            }
//        }
//    }

}