package com.ceydavardar.yumyumapp.data.entity

data class WholeCartFoods(
    var foodName: String,
    var cartFoodList: MutableList<CartFood>,
    var totalPrice: Int,
    var totalAmount: Int
) {
}