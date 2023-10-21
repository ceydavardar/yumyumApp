package com.ceydavardar.yumyumapp.data.entity

import java.io.Serializable

data class Food(
    val yemekId: Int,
    val yemekAdi: String,
    val yemekResimAdi: String,
    val yemekFiyat: Int
): Serializable {
}