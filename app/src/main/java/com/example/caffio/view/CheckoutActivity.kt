package com.example.caffio.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.caffio.ui.theme.CaffioTheme
import com.google.firebase.database.FirebaseDatabase

class CheckoutActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val productName = intent.getStringExtra("productName") ?: "Selected Coffee"
        val productPrice = intent.getDoubleExtra("productPrice", 0.0)
        val productImageRes = intent.getIntExtra("productImageRes", 0)

        setContent {
            CaffioTheme {
                CheckoutScreen(
                    productName = productName,
                    productPrice = productPrice,
                    productImageRes = productImageRes,
                    onBackClick = { finish() },
                    onConfirmPurchase = { selectedPaymentMethod, deliveryAddress ->
                        if (deliveryAddress.isBlank()) {
                            Toast.makeText(this, "Please enter your delivery address.", Toast.LENGTH_SHORT).show()
                        } else {
                            val orderId = FirebaseDatabase.getInstance().reference.push().key ?: "order_${System.currentTimeMillis()}"
                            val orderMap = mapOf(
                                "orderId" to orderId,
                                "productName" to productName,
                                "productPrice" to productPrice,
                                "paymentMethod" to selectedPaymentMethod,
                                "deliveryAddress" to deliveryAddress,
                                "timestamp" to System.currentTimeMillis()
                            )

                            FirebaseDatabase.getInstance().getReference("orders")
                                .child(orderId)
                                .setValue(orderMap)
                                .addOnSuccessListener {
                                    Toast.makeText(this, "Order Placed Successfully!", Toast.LENGTH_LONG).show()
                                    val intent = Intent(this, DashboardActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                                    startActivity(intent)
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(this, "Failed to place order: ${e.message}", Toast.LENGTH_LONG).show()
                                }
                        }
                    }
                )
            }
        }
    }
}
