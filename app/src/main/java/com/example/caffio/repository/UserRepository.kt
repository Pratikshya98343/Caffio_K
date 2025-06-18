package com.example.caffio.repository

import com.example.caffio.model.UserModel
import com.google.firebase.auth.FirebaseUser
import javax.security.auth.callback.Callback

interface UserRepository {
    //login
    //register
    //forgotPassword
    //updateProfile
    //getCurrentUser
    //addUserToDatabase
    //logout
    //{
    //  "success": true,
    //  "message": "login successful",
    //  "statusCode": 200

    fun login(
        email: String, password: String,
        callback: (Boolean, String) -> Unit
    )

    //authentication function
    fun register(
        email: String, password: String,
        callback: (Boolean, String, String) -> Unit
    )

    //database function
    fun addUserToDatabase(
        userId: String, model: UserModel,
        callback: (Boolean, String) -> Unit
    )

    fun updateProfile(userId: String,data : MutableMap<String,Any?>,
                      callback: (Boolean, String) -> Unit)

    fun forgetPassword(
        email: String, callback: (Boolean, String) -> Unit
    )

    fun getCurrentUser(): FirebaseUser?

    fun getUserById(
        userId: String,
        callback: (
            UserModel?,
            Boolean, String
        ) -> Unit
    )



    fun logout(callback: (Boolean, String) -> Unit)
}