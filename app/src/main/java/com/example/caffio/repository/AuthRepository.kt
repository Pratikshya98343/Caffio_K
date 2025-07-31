package com.example.caffio.repository

interface AuthRepository {
    fun login(
        email: String, password: String,
        callback: (Boolean, String) -> Unit
    )

    //authentication function
    fun register(
        email: String, password: String,
        callback: (Boolean, String, String) -> Unit
    )
}