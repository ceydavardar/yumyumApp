package com.ceydavardar.yumyumapp.data.entity

data class FoodsResponse(
    val yemekler: List<FoodResponse>,
    val success: Int
) {
}