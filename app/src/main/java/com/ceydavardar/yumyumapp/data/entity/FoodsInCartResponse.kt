package com.ceydavardar.yumyumapp.data.entity

data class FoodsInCartResponse(
    val sepet_yemekler: List<CartFoodResponse>,
    val success: Int
) {
}