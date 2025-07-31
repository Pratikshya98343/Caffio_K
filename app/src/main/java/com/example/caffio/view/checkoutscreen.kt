package com.example.caffio.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CheckoutScreen(
    productName: String,
    productPrice: Double,
    productImageRes: Int,
    onBackClick: () -> Unit,
    onConfirmPurchase: (String, String) -> Unit
) {
    var paymentMethod by remember { mutableStateOf("Cash on Delivery") }
    var deliveryAddress by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Checkout", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(id = productImageRes),
            contentDescription = "Coffee Image",
            modifier = Modifier
                .size(150.dp)
                .clip(RoundedCornerShape(16.dp))
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(productName, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        Text("Price: Rs. %.2f".format(productPrice), fontSize = 16.sp)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = deliveryAddress,
            onValueChange = { deliveryAddress = it },
            label = { Text("Delivery Address") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Select Payment Method")
        Spacer(modifier = Modifier.height(8.dp))

        PaymentMethodSelector(paymentMethod) { selected ->
            paymentMethod = selected
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { onConfirmPurchase(paymentMethod, deliveryAddress) },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6F4E37)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Confirm Purchase", color = Color.White)
        }

        Spacer(modifier = Modifier.height(12.dp))

        TextButton(onClick = onBackClick) {
            Text("Go Back")
        }
    }
}

@Composable
fun PaymentMethodSelector(current: String, onSelected: (String) -> Unit) {
    val options = listOf("Cash on Delivery", "Card Payment")
    Column {
        options.forEach { option ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = current == option,
                    onClick = { onSelected(option) }
                )
                Text(option)
            }
        }
    }
}
