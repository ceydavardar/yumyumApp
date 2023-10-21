package com.ceydavardar.yumyumapp.data.entity

data class CartFoodResponse(
    val sepet_yemek_id: String,
    val yemek_adi: String,
    val yemek_resim_adi: String,
    val yemek_fiyat: String,
    val yemek_siparis_adet: String,
    val kullanici_adi: String
) {
}

fun CartFoodResponse.toCartFood(): CartFood {
    return CartFood(this.sepet_yemek_id.toInt(),this.yemek_adi,this.yemek_resim_adi,this.yemek_fiyat.toInt(),this.yemek_siparis_adet.toInt(), this.kullanici_adi)
}