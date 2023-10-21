package com.ceydavardar.yumyumapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ceydavardar.yumyumapp.data.entity.Food
import com.ceydavardar.yumyumapp.data.repo.FoodsRepository
import com.ceydavardar.yumyumapp.data.repo.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    val repo: FoodsRepository,
    var userRepo: UserRepository
): ViewModel() {

    val amount = MutableLiveData<Int>()

    init {
        amount.value = 0
    }

    fun addTrigger() {
        var localAmount = 1
        if (amount.value == null) {
            localAmount = 1
        } else {
            if (amount.value!! > 0) {
                localAmount = amount.value!! + 1
            }
        }
        amount.value = localAmount
    }

    fun removeTrigger() {
        var localAmount = 0
        if (amount.value == null) {
            localAmount = 0
        } else {
            if (amount.value!! > 0) {
                localAmount = amount.value!! - 1
            }
        }
        amount.value = localAmount
    }

    fun addFoodToCart(food: Food, amount: Int, callback: (Boolean) -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = repo.addFoodToCart(
                    food.yemekAdi,
                    food.yemekResimAdi,
                    food.yemekFiyat,
                    amount,
                    userRepo.username!!
                )
                if (response.success == 0) {
                    callback(false)
                } else {
                    callback(true)
                }
            } catch (e: Exception) {
                callback(false)
            }
        }
    }

}