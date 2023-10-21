package com.ceydavardar.yumyumapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ceydavardar.yumyumapp.data.entity.WholeCartFoods
import com.ceydavardar.yumyumapp.data.entity.toCartFood
import com.ceydavardar.yumyumapp.data.repo.FoodsRepository
import com.ceydavardar.yumyumapp.data.repo.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    var repo: FoodsRepository,
    var userRepo: UserRepository
): ViewModel() {

    var data = MutableLiveData<List<WholeCartFoods>>()

    init {
        loadCartFoods(userRepo.username!!) {
            if (it) {
                Log.e("Yumyum", "Sepetteki yemekler başarıyla yüklendi.")
            } else {
                Log.e("Yumyum", "Sepetteki yemekler yüklenemedi.")
            }
        }
    }

    fun loadCartFoods(username: String, callback: (Boolean) -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = repo.getCartFoods(username)
                if (response.success == 0) {
                    data.value = emptyList()
                    callback(false)
                } else {
                    val yeniYemekListesi = mutableListOf<WholeCartFoods>()

                    for (cartFood in response.sepet_yemekler) {
                        val varOlan = yeniYemekListesi.find { it.foodName == cartFood.yemek_adi }

                        if (varOlan == null) {
                            val yeniGiris = WholeCartFoods(
                                cartFood.yemek_adi,
                                mutableListOf(cartFood.toCartFood()),
                                cartFood.yemek_fiyat.toInt()*cartFood.yemek_siparis_adet.toInt(),
                                cartFood.yemek_siparis_adet.toInt()
                            )
                            yeniYemekListesi.add(yeniGiris)
                            continue
                        } else {
                            varOlan.cartFoodList.add(cartFood.toCartFood())
                            varOlan.totalAmount += cartFood.yemek_siparis_adet.toInt()
                            varOlan.totalPrice = cartFood.yemek_fiyat.toInt()*varOlan.totalAmount
                        }
                    }

                    data.value = yeniYemekListesi

                }
            } catch (e: Exception) {
                data.value = emptyList()
                callback(false)
            }
        }
    }

    suspend fun removeFoodFromCart(wholeCartFoods: WholeCartFoods): Boolean {
        val deferredResults = wholeCartFoods.cartFoodList.map { cartFood ->
            CoroutineScope(Dispatchers.IO).async {
                try {
                    val response = repo.removeCartFood(
                        cartFood.sepet_yemek_id,
                        cartFood.kullanici_adi
                    )
                    return@async response.success == 1
                } catch (e: Exception) {
                    false
                }
            }
        }

        val results = deferredResults.awaitAll()
        return results.all { it }
    }

}