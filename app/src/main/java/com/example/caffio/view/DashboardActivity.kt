package com.example.caffio.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.caffio.R
import com.example.caffio.ui.theme.CaffioTheme
import kotlinx.coroutines.delay

// Make sure this import points to your actual LoginActivity
// If LoginActivity is in a different package, adjust the import accordingly
// import com.example.yourapp.LoginActivity // Example alternative import

class DashboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CaffioTheme {
                UserDashboard()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDashboard() {
    val context = LocalContext.current
    val activity = context as Activity
    // Use Elvis operator for null safety and capitalize the first part of the email
    val email: String = activity.intent.getStringExtra("email")?.split('@')?.get(0)?.capitalize() ?: "Coffee Lover"
    var isVisible by remember { mutableStateOf(false) }
    // Coffee-themed gradient colors (browns and warm tones)
    val profileBackgroundGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF3E2723), // Dark Brown
            Color(0xFF4E342E), // Medium Brown
            Color(0xFF5D4037), // Coffee Brown
            Color(0xFF6D4C41)  // Light Coffee Brown
        )
    )
    LaunchedEffect(Unit) {
        delay(400)
        isVisible = true
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { /* No title needed here as we have a custom greeting */ },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                actions = {
                    // --- Logout Button ---
                    IconButton(onClick = {
                        // Clear activity stack and navigate to LoginActivity
                        // Make sure LoginActivity exists and the import is correct
                        val intent = Intent(context, LoginActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            // Optional: Add an extra to indicate logout if needed in LoginActivity
                            // putExtra("USER_LOGGED_OUT", true)
                        }
                        context.startActivity(intent)
                        activity.finish() // Finish the current DashboardActivity
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ExitToApp, // Use the Logout icon
                            contentDescription = "Logout",
                            tint = Color.White // You can change the color if desired
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFF8D6E63), // Coffee brown for navigation
                contentColor = Color.White,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = true,
                    onClick = { /* Stay on Dashboard */ },
                    icon = { Icon(painterResource(R.drawable.baseline_home_24), contentDescription = "Home") },
                    label = { Text("Home") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = {
                        context.startActivity(Intent(context, FavouriteActivity::class.java))
                    },
                    icon = { Icon(painterResource(R.drawable.baseline_favorite_24), contentDescription = "Favorites") },
                    label = { Text("Favorites") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = {
                        context.startActivity(Intent(context, ProfileActivity::class.java))
                    },
                    icon = { Icon(painterResource(R.drawable.baseline_person_24), contentDescription = "Profile") },
                    label = { Text("Profile") }
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(profileBackgroundGradient)
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                AnimatedVisibility(visible = isVisible, enter = fadeIn()) {
                    Text(
                        text = "Good Morning, $email!",
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold),
                        color = Color.White
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White)
                        .shadow(8.dp, RoundedCornerShape(16.dp))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.background),
                        contentDescription = "Coffee Hero",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, Color(0xAA3E2723)), // Coffee brown overlay
                                    startY = 100f
                                )
                            )
                    )
                    Text(
                        text = "Discover Premium Coffee Blends",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        ),
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp)
                    )
                }
                Text(
                    text = "☕ Featured Coffee Blends",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = Color.White
                )
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(sampleCoffeeBlends) { coffee ->
                        // Pass the whole CoffeeBlend object to the card and define its click behavior
                        FeaturedCoffeeCard(coffee = coffee) { clickedCoffee ->
                            // Handle click: Navigate to ProductDetailActivity
                            val intent = Intent(context, ProductDetailActivity::class.java).apply {
                                // Pass product details as extras
                                putExtra("coffeeName", clickedCoffee.name)
                                putExtra("coffeeDescription", clickedCoffee.description)
                                putExtra("coffeeImageRes", clickedCoffee.imageRes)
                                putExtra("coffeePrice", clickedCoffee.price) // Pass the price
                                putExtra("coffeeRoast", clickedCoffee.roastLevel) // Pass roast level
                            }
                            context.startActivity(intent)
                        }
                    }
                }
            }
            // Plus icon button at the bottom right
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(24.dp)
            ) {
                FloatingActionButton(
                    onClick = {
                        // Navigate to AddProductActivity
                        context.startActivity(Intent(context, AddProductActivity::class.java))
                    },
                    containerColor = Color(0xFF8D6E63), // Coffee brown
                    contentColor = Color.White,
                    shape = CircleShape,
                    modifier = Modifier.size(56.dp)
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Add Coffee Product")
                }
            }
        }
    }
}

// Enhanced CoffeeBlend data class with price and roast level
data class CoffeeBlend(
    val name: String,
    val description: String,
    val imageRes: Int,
    val price: Double,
    val roastLevel: String
)

val sampleCoffeeBlends = listOf(
    CoffeeBlend(
        "Cappuccino White Foam Coffee for Everyone",
        "Bright and floral notes with hints of citrus and tea-like qualities. A premium single-origin coffee with wine-like acidity and complex flavor profile.",
        R.drawable.img_1,
        450.00,
        "Light Roast"
    ),
    CoffeeBlend(
        "Classic Espresso Single Shot Premium Blend",
        "Rich, full-bodied coffee with chocolate undertones and nutty finish. Perfectly balanced acidity with caramel sweetness that makes it ideal for any time of day.",
        R.drawable.img_2,
        380.00,
        "Medium Roast"
    ),
    CoffeeBlend(
        "Americano Light Brown Coffee for Daily Energy",
        "Bold and intense with deep, smoky flavors. A classic dark roast blend perfect for espresso shots, cappuccinos, and lattes. Strong caffeine kick guaranteed.",
        R.drawable.img_3,
        520.00,
        "Dark Roast"
    ),
    CoffeeBlend(
        "Coffee Frappuccino Iced Coffee Special Edition",
        "Smooth and mellow with low acidity and nutty chocolate notes. An excellent everyday coffee that's gentle on the stomach yet rich in flavor and aroma.",
        R.drawable.img_4,
        320.00,
        "Medium Roast"
    ),
    CoffeeBlend(
        "Coffee Frappuccino Special Edition",
        "The world's most sought-after coffee with exceptional smoothness and no bitterness. Mild yet complex with floral aroma and perfect balance.",
        R.drawable.img_5,
        850.00,
        "Medium Roast"
    )
)

@Composable
fun FeaturedCoffeeCard(coffee: CoffeeBlend, onClick: (CoffeeBlend) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .clickable { onClick(coffee) }, // Make the card clickable
        colors = CardDefaults.cardColors(containerColor = Color(0x30FFFFFF)), // Semi-transparent white
        elevation = CardDefaults.cardElevation(6.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = coffee.imageRes),
                contentDescription = coffee.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(96.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxHeight()
            ) {
                Text(
                    text = coffee.name,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = coffee.roastLevel,
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                    color = Color(0xFFFFCC80) // Light orange for roast level
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = coffee.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.7f),
                    maxLines = 2, // Limit lines to keep card height consistent
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis // Add ellipsis for overflow
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Rs. ${String.format("%.2f", coffee.price)}",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                    color = Color(0xFFFFCC80) // Light orange for price to make it stand out
                )
            }
        }
    }
}