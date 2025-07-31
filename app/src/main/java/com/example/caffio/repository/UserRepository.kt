package com.example.caffio.repository

import android.net.wifi.aware.AttachCallback
import com.example.caffio.model.UserModel
import com.google.firebase.auth.FirebaseUser

interface UserRepo {
    //login
    // Register
    // forget password
    //update profile
    //update profile
    //get CurrentUser
    // addUserToDatabase
    //logout


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