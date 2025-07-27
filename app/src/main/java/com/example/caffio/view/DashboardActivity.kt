package com.example.caffio.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.caffio.R
import com.example.caffio.repository.ProductRepositoryImpl
import com.example.caffio.viewmodel.ProductViewModel
import java.util.Calendar

class DashboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DashboardBody()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardBody() {
    val context = LocalContext.current
    val activity = context as? Activity

    val repo = remember { ProductRepositoryImpl() }
    val viewModel = remember { ProductViewModel(repo) }

    val products = viewModel.allProducts.observeAsState(initial = emptyList())
    val loading = viewModel.loading.observeAsState(initial = true)

    // State for favorites
    var favorites by remember { mutableStateOf(setOf<String>()) }

    // State for selected navigation item
    var selectedItem by remember { mutableStateOf(0) } // 0 for Home, 1 for Favorites, 2 for Profile

    LaunchedEffect(Unit) {
        viewModel.getAllProduct()
    }

    // Coffee Color Theme - Rich browns and warm tones
    val coffeeBackground = Color(0xFF2C1810) // Dark coffee brown
    val coffeeCardColor = Color(0xFF3E2723) // Rich brown
    val coffeePrimary = Color(0xFF6F4E37) // Coffee brown
    val coffeeSecondary = Color(0xFF8D6E63) // Lighter brown
    val coffeeAccent = Color(0xFFD7CCC8) // Cream
    val coffeeText = Color(0xFFEFEBE9) // Light cream
    val coffeeTextSecondary = Color(0xFFBCAAA4) // Muted cream
    val coffeeHighlight = Color(0xFFFF8A65) // Warm orange
    val coffeeError = Color(0xFFD32F2F) // Red for delete
    val coffeeGradient = Brush.verticalGradient(
        colors = listOf(
            coffeeBackground,
            Color(0xFF1B0F0A) // Even darker coffee
        )
    )

    // Get greeting message
    val greeting = when (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) {
        in 0..11 -> "Good Morning"
        in 12..17 -> "Good Afternoon"
        else -> "Good Evening"
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            greeting,
                            color = coffeeText,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal
                        )
                        Text(
                            "Welcome to Caffio",
                            color = coffeeAccent,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = coffeePrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val intent = Intent(context, AddProductActivity::class.java)
                    context.startActivity(intent)
                },
                containerColor = coffeeHighlight,
                contentColor = Color.White,
                modifier = Modifier.shadow(16.dp, CircleShape)
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add Product",
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        bottomBar = {
            NavigationBar(
                containerColor = coffeePrimary,
                contentColor = coffeeText
            ) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home", fontSize = 12.sp) },
                    selected = selectedItem == 0, // Highlight if selected
                    onClick = { selectedItem = 0 }, // Set selected item
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = coffeeAccent,
                        selectedTextColor = coffeeAccent,
                        unselectedIconColor = coffeeTextSecondary,
                        unselectedTextColor = coffeeTextSecondary
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Favorite, contentDescription = "Favorites") },
                    label = { Text("Favorites", fontSize = 12.sp) },
                    selected = selectedItem == 1, // Highlight if selected
                    onClick = {
                        selectedItem = 1 // Set selected item
                        Toast.makeText(
                            context,
                            "Favorites: ${favorites.size} items",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = coffeeAccent,
                        selectedTextColor = coffeeAccent,
                        unselectedIconColor = coffeeTextSecondary,
                        unselectedTextColor = coffeeTextSecondary
                    )
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                    label = { Text("Profile", fontSize = 12.sp) },
                    selected = selectedItem == 2, // Highlight if selected
                    onClick = {
                        selectedItem = 2 // Set selected item
                        // Navigate to ProfileActivity
                        val intent = Intent(context, ProfileActivity::class.java)
                        context.startActivity(intent)
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = coffeeAccent,
                        selectedTextColor = coffeeAccent,
                        unselectedIconColor = coffeeTextSecondary,
                        unselectedTextColor = coffeeTextSecondary
                    )
                )
            }
        },
        containerColor = coffeeBackground
    ) { innerPadding ->

        if (loading.value) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(coffeeGradient),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        color = coffeeHighlight,
                        strokeWidth = 4.dp,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "Brewing your coffee collection...",
                        color = coffeeText,
                        fontSize = 16.sp
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(coffeeGradient),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp)
            ) {
                // Coffee Hero Section
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        colors = CardDefaults.cardColors(containerColor = coffeeCardColor),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            // Background Image
                            Image(
                                painter = painterResource(R.drawable.background), // Add your coffee background image here
                                contentDescription = "Coffee Background",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )

                            // Gradient Overlay for better text readability
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        brush = Brush.horizontalGradient(
                                            colors = listOf(
                                                coffeeBackground.copy(alpha = 0.85f),
                                                coffeePrimary.copy(alpha = 0.75f),
                                                Color.Transparent
                                            )
                                        )
                                    )
                            )

                            // Content Row
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(24.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        "Discover Premium Coffee",
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = coffeeAccent,
                                        modifier = Modifier.shadow(2.dp)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        "Experience the perfect blend of rich flavors and aromatic excellence. From single-origin beans to artisanal blends, every cup tells a story.",
                                        fontSize = 14.sp,
                                        color = coffeeText,
                                        lineHeight = 20.sp,
                                        modifier = Modifier.shadow(1.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(16.dp))

                                // Coffee Cup Icon/Image
                                Box(
                                    modifier = Modifier
                                        .size(80.dp)
                                        .clip(CircleShape)
                                        .background(
                                            brush = Brush.radialGradient(
                                                colors = listOf(
                                                    coffeeHighlight.copy(alpha = 0.4f),
                                                    Color.Transparent
                                                )
                                            )
                                        )
                                        .shadow(8.dp, CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.ic_launcher_foreground),
                                        contentDescription = "Coffee",
                                        modifier = Modifier.size(50.dp),
                                        tint = coffeeAccent
                                    )
                                }
                            }
                        }
                    }
                }

                item { Spacer(modifier = Modifier.height(24.dp)) }

                // Stats Cards Row
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Total Products Card
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .height(80.dp),
                            colors = CardDefaults.cardColors(containerColor = coffeeCardColor),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(12.dp),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    "Total Products",
                                    color = coffeeTextSecondary,
                                    fontSize = 12.sp
                                )
                                Text(
                                    "${products.value.size}",
                                    color = coffeeHighlight,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        // Favorites Card
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .height(80.dp),
                            colors = CardDefaults.cardColors(containerColor = coffeeCardColor),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(12.dp),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    "Favorites",
                                    color = coffeeTextSecondary,
                                    fontSize = 12.sp
                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        Icons.Default.Favorite,
                                        contentDescription = null,
                                        tint = Color.Red.copy(alpha = 0.7f),
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        "${favorites.size}",
                                        color = coffeeHighlight,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }

                item { Spacer(modifier = Modifier.height(24.dp)) }

                // Section Header
                item {
                    Text(
                        "Your Coffee Collection",
                        color = coffeeAccent,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Products Grid
                if (products.value.isEmpty()) {
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 32.dp),
                            colors = CardDefaults.cardColors(containerColor = coffeeCardColor),
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(48.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(80.dp)
                                        .background(
                                            color = coffeeSecondary.copy(alpha = 0.3f),
                                            shape = CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.gallery9),
                                        contentDescription = null,
                                        modifier = Modifier.size(40.dp),
                                        tint = coffeeTextSecondary
                                    )
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    "No Coffee Yet",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = coffeeText
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    "Add your first coffee product to start your collection",
                                    fontSize = 14.sp,
                                    color = coffeeTextSecondary,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                } else {
                    items(products.value.chunked(2).size) { rowIndex ->
                        val rowItems = products.value.chunked(2)[rowIndex]

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            rowItems.forEach { data ->
                                val productId = data?.productId.toString()
                                val isFavorite = favorites.contains(productId)

                                Card(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(220.dp),
                                    colors = CardDefaults.cardColors(containerColor = coffeeCardColor),
                                    shape = RoundedCornerShape(20.dp)
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(16.dp)
                                    ) {
                                        // Product Image and Favorite with Background
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(80.dp)
                                                .clip(RoundedCornerShape(12.dp))
                                        ) {
                                            // Background Image - you can customize this per product
                                            val backgroundImage =
                                                when (data?.productName?.lowercase()) {
                                                    "espresso" -> R.drawable.background
                                                    "cappuccino" -> R.drawable.background
                                                    "latte" -> R.drawable.background
                                                    else -> R.drawable.background
                                                }

                                            Image(
                                                painter = painterResource(backgroundImage),
                                                contentDescription = "Product Background",
                                                modifier = Modifier.fillMaxSize(),
                                                contentScale = ContentScale.Crop
                                            )

                                            // Gradient Overlay for better icon visibility
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .background(
                                                        brush = Brush.verticalGradient(
                                                            colors = listOf(
                                                                Color.Transparent,
                                                                coffeeBackground.copy(alpha = 0.3f),
                                                                coffeeBackground.copy(alpha = 0.6f)
                                                            )
                                                        )
                                                    )
                                            )

                                            // Product Icon (overlay on background)
                                            // Favorite Button
                                            IconButton(
                                                onClick = {
                                                    favorites = if (isFavorite) {
                                                        favorites - productId
                                                    } else {
                                                        favorites + productId
                                                    }
                                                },
                                                modifier = Modifier
                                                    .align(Alignment.TopEnd)
                                                    .size(32.dp)
                                                    .background(
                                                        color = coffeeBackground.copy(alpha = 0.7f),
                                                        shape = CircleShape
                                                    )
                                            ) {
                                                Icon(
                                                    if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                                    contentDescription = "Favorite",
                                                    tint = if (isFavorite) Color.Red else coffeeTextSecondary,
                                                    modifier = Modifier.size(18.dp)
                                                )
                                            }
                                        }

                                        Spacer(modifier = Modifier.height(12.dp))

                                        // Product Name
                                        Text(
                                            text = data?.productName ?: "Unknown",
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = coffeeText,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )

                                        // Product Description
                                        Text(
                                            text = data?.productDesc ?: "No description",
                                            fontSize = 15.sp,
                                            color = coffeeTextSecondary,
                                            maxLines = 2,
                                            overflow = TextOverflow.Ellipsis
                                        )

                                        Spacer(modifier = Modifier.weight(1f))
                                    }

                                }
                            }
                        }
                    }


                }
            }

        }
    }
}


        @Preview
        @Composable
        fun DashboardActivityPre() {
            DashboardBody()
        }
