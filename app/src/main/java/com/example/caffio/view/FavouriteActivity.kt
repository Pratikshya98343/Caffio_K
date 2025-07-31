package com.example.caffio.view

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.caffio.ui.theme.CaffioTheme
import com.google.firebase.database.FirebaseDatabase

class FavouriteActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CaffioTheme {
                FavouriteCoffeeBody()
            }
        }
    }
}

data class FavouriteCoffee(
    val coffeeName: String = "",
    val coffeeType: String = "",
    val description: String = ""
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouriteCoffeeBody() {
    val context = LocalContext.current
    val activity = context as? Activity

    var coffeeName by remember { mutableStateOf("") }
    var coffeeType by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val profileBackgroundGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF3E2723),
            Color(0xFF4E342E),
            Color(0xFF5D4037),
            Color(0xFF6D4C41)
        )
    )

    val cardContentTextColor = Color(0xFF3E2723)
    val cardContentAccentColor = Color(0xFF8D6E63)
    val buttonColor = Color(0xFF8D6E63)
    val cardBackgroundColor = Color(0xFFFFF8F5)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(profileBackgroundGradient)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "☕ Favourite Coffee",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                IconButton(onClick = { activity?.finish() }) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = cardBackgroundColor),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "☕ Coffee Details",
                        color = cardContentTextColor,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    OutlinedTextField(
                        value = coffeeName,
                        onValueChange = { coffeeName = it },
                        label = { Text("Coffee Name", color = cardContentTextColor.copy(alpha = 0.7f)) },
                        placeholder = { Text("Enter your favourite coffee", color = cardContentTextColor.copy(alpha = 0.5f)) },
                        leadingIcon = {
                            Icon(Icons.Filled.Favorite, contentDescription = null, tint = cardContentAccentColor)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = cardContentTextColor,
                            unfocusedTextColor = cardContentTextColor,
                            focusedBorderColor = cardContentAccentColor,
                            unfocusedBorderColor = cardContentTextColor.copy(alpha = 0.3f),
                            cursorColor = cardContentAccentColor
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = coffeeType,
                        onValueChange = { coffeeType = it },
                        label = { Text("Coffee Type", color = cardContentTextColor.copy(alpha = 0.7f)) },
                        placeholder = { Text("e.g., Espresso, Americano, Latte", color = cardContentTextColor.copy(alpha = 0.5f)) },
                        leadingIcon = {
                            Icon(Icons.Filled.Favorite, contentDescription = null, tint = cardContentAccentColor)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = cardContentTextColor,
                            unfocusedTextColor = cardContentTextColor,
                            focusedBorderColor = cardContentAccentColor,
                            unfocusedBorderColor = cardContentTextColor.copy(alpha = 0.3f),
                            cursorColor = cardContentAccentColor
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Why you love it", color = cardContentTextColor.copy(alpha = 0.7f)) },
                        placeholder = { Text("Describe why this coffee is your favourite", color = cardContentTextColor.copy(alpha = 0.5f)) },
                        leadingIcon = {
                            Icon(Icons.Filled.Star, contentDescription = null, tint = cardContentAccentColor)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3,
                        maxLines = 5,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = cardContentTextColor,
                            unfocusedTextColor = cardContentTextColor,
                            focusedBorderColor = cardContentAccentColor,
                            unfocusedBorderColor = cardContentTextColor.copy(alpha = 0.3f),
                            cursorColor = cardContentAccentColor
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    if (coffeeName.isBlank() || coffeeType.isBlank()) {
                        Toast.makeText(context, "Please fill in all required fields", Toast.LENGTH_LONG).show()
                        return@Button
                    }

                    isLoading = true

                    val database = FirebaseDatabase.getInstance()
                    val coffeesRef = database.reference.child("favourite_coffees")

                    val coffeeId = coffeesRef.push().key

                    if (coffeeId == null) {
                        Toast.makeText(context, "Failed to generate database key", Toast.LENGTH_LONG).show()
                        isLoading = false
                        return@Button
                    }

                    val coffee = FavouriteCoffee(coffeeName, coffeeType, description)

                    coffeesRef.child(coffeeId).setValue(coffee)
                        .addOnSuccessListener {
                            Toast.makeText(context, "Favourite coffee saved: $coffeeName", Toast.LENGTH_LONG).show()
                            isLoading = false
                            activity?.finish()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(context, "Failed to save: ${e.message}", Toast.LENGTH_LONG).show()
                            isLoading = false
                        }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = !isLoading,
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                shape = RoundedCornerShape(16.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Saving...", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.White)
                } else {
                    Icon(Icons.Filled.Check, contentDescription = null, modifier = Modifier.size(20.dp), tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("☕ Save Favourite Coffee", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
