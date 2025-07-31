package com.example.caffio.model

data class Order(
    val productName: String = "",
    val productPrice: Double = 0.0,
    val paymentMethod: String = "",
    val deliveryAddress: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
