package com.ceydavardar.yumyumapp.data.repo

import com.ceydavardar.yumyumapp.data.datasource.FoodsDataSource
import com.ceydavardar.yumyumapp.data.entity.CRUDResponse
import com.ceydavardar.yumyumapp.data.entity.FoodsInCartResponse
import com.ceydavardar.yumyumapp.data.entity.FoodsResponse

class FoodsRepository(var fds: FoodsDataSource) {

    suspend fun getFoods(): FoodsResponse {
        return fds.getFoods()
    }

    suspend fun getCartFoods(kullanici_adi: String): FoodsInCartResponse {
        return fds.getCartFoods(kullanici_adi)
    }

    suspend fun addFoodToCart(yemek_adi: String, yemek_resim_adi: String, yemek_fiyat: Int, yemek_siparis_adet: Int, kullanici_adi: String):
            CRUDResponse {
        return fds.addFoodToCart(yemek_adi,yemek_resim_adi,yemek_fiyat,yemek_siparis_adet,kullanici_adi)
    }

    suspend fun removeCartFood(sepet_yemek_id: Int, kullanici_adi: String): CRUDResponse {
        return fds.removeCartFood(sepet_yemek_id, kullanici_adi)
    }

}