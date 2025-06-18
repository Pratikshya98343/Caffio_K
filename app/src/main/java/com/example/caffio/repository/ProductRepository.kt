package com.example.caffio.repository

import android.content.Context
import android.net.Uri
import com.example.caffio.model.ProductModel

interface ProductRepository {
    //addProduct
    //updateProduct
    //deleteProduct
    //getAllProducts
    //getProductById

    fun addProduct(
        model:ProductModel,
        callback: (Boolean, String) -> Unit
    )

    fun updateProduct(
        productId: String,
        data: MutableMap<String, Any?>,
        callback: (Boolean, String) -> Unit
    )

    fun deleteProduct(
        productId: String,
        callback: (Boolean, String) -> Unit

    )

    fun getAllProducts(
        callback: (Boolean, String, List<ProductModel?>) -> Unit
    )

    fun getProductById(
        productId: String,
        callback: (Boolean, String, ProductModel?) -> Unit
    )

    fun uploadImage(context: Context,imageUri: Uri, callback: (String?) -> Unit)

    fun getFileNameFromUri(context: Context,uri: Uri): String?
}
