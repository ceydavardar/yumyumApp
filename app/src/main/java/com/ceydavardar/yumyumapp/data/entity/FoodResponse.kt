package com.ceydavardar.yumyumapp.data.entity

import java.io.Serializable

data class FoodResponse(
    val yemek_id: String,
    val yemek_adi: String,
    val yemek_resim_adi: String,
    val yemek_fiyat: String
): Serializable {
}