package com.ceydavardar.yumyumapp.data.datasource

import com.ceydavardar.yumyumapp.data.entity.CRUDResponse
import com.ceydavardar.yumyumapp.data.entity.FoodsInCartResponse
import com.ceydavardar.yumyumapp.data.entity.FoodsResponse
import com.ceydavardar.yumyumapp.retrofit.FoodsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FoodsDataSource(var fdao: FoodsDao) {

    suspend fun getFoods(): FoodsResponse = withContext(Dispatchers.IO) {
        return@withContext fdao.getFoods()
    }

    suspend fun addFoodToCart(yemek_adi: String, yemek_resim_adi: String, yemek_fiyat: Int, yemek_siparis_adet: Int, kullanici_adi: String):
            CRUDResponse = withContext(Dispatchers.IO) {
        return@withContext fdao.addFoodToCart(yemek_adi,yemek_resim_adi,yemek_fiyat,yemek_siparis_adet,kullanici_adi)
    }

    suspend fun getCartFoods(kullanici_adi: String): FoodsInCartResponse = withContext(Dispatchers.IO) {
        return@withContext fdao.getFoodsInCart(kullanici_adi)
    }

    suspend fun removeCartFood(sepet_yemek_id: Int, kullanici_adi: String): CRUDResponse = withContext(Dispatchers.IO) {
        return@withContext fdao.removeCartFood(sepet_yemek_id, kullanici_adi)
    }

}