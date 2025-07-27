package com.example.caffio.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.caffio.view.ui.theme.CaffioTheme
import kotlinx.coroutines.delay

class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CaffioTheme {
                ProfileApp()
            }
        }
    }
}
sealed class Screen {
    object Profile : Screen()
    object EditProfile : Screen()
    object ChangePassword : Screen()
    object CoffeePreferences : Screen()
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileApp() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Profile) }
    val context = LocalContext.current // Get context for Toast messages

    when (currentScreen) {
        Screen.Profile -> ProfileScreen(
            onNavigate = { currentScreen = it },
            onBack = {
                // For a single activity app, this will finish the activity
                // In a multi-activity app or with a NavHost, you'd navigate up
                (context as? ComponentActivity)?.finish()
            }
        )
        Screen.EditProfile -> EditProfileScreen(
            onBack = { currentScreen = Screen.Profile },
            onSaveSuccess = { message ->
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                currentScreen = Screen.Profile
            },
            onSaveError = { message ->
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        )
        Screen.ChangePassword -> ChangePasswordScreen(
            onBack = { currentScreen = Screen.Profile },
            onSaveSuccess = { message ->
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                currentScreen = Screen.Profile
            },
            onSaveError = { message ->
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        )
        Screen.CoffeePreferences -> CoffeePreferencesScreen(
            onBack = { currentScreen = Screen.Profile },
            onSaveSuccess = { message ->
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                currentScreen = Screen.Profile
            },
            onSaveError = { message ->
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        )
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(onNavigate: (Screen) -> Unit, onBack: () -> Unit) {
    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF6F4E37), Color(0xFF8B4513), // Coffee-like colors
            Color(0xFFA0522D), Color(0xFFD2B48C)
        )
    )
    val mockUser = UserModel(fullName = "Coffee Lover", email = "user@caffio.com") // Changed email
    var currentUser by remember { mutableStateOf(mockUser) }
    var isLoading by remember { mutableStateOf(false) } // This isLoading is for the initial load, not saving

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "My Profile",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) { // Calls the onBack lambda
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        containerColor = Color.Transparent
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
                .padding(paddingValues)
        ) {
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFFCD853F)) // Coffee-like color
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Box(
                        modifier = Modifier
                            .size(140.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.radialGradient(
                                    colors = listOf(
                                        Color(0xFFCD853F), Color(0xFFD2B48C), Color(0xFFF5DEB3) // Coffee-like colors
                                    )
                                )
                            )
                            .border(4.dp, Color(0xFFA0522D), CircleShape), // Coffee-like color
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = currentUser.fullName.firstOrNull()?.toString()?.uppercase() ?: "U",
                            color = Color(0xFF6F4E37), fontWeight = FontWeight.Bold, fontSize = 56.sp // Coffee-like color
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = currentUser.fullName,
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = currentUser.email,
                        color = Color(0xFFF5DEB3), // Coffee-like color
                        fontSize = 16.sp,
                        fontStyle = FontStyle.Italic
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Card(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0x60A0522D)), // Coffee-like color
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "☕", fontSize = 20.sp) // Changed emoji
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Espresso Enthusiast", // Changed text
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                    ProfileOptionCard(
                        icon = Icons.Default.Edit,
                        title = "Edit Profile",
                        subtitle = "Update your coffee preferences & info", // Changed subtitle
                        onClick = { onNavigate(Screen.EditProfile) }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    ProfileOptionCard(
                        icon = Icons.Default.Lock,
                        title = "Change Password",
                        subtitle = "Secure your Caffio account", // Changed subtitle
                        onClick = { onNavigate(Screen.ChangePassword) },
                        accentColor = Color(0xFFCD853F) // Coffee-like color
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    ProfileOptionCard(
                        icon = Icons.Default.CheckCircle,
                        title = "Coffee Preferences", // Changed title
                        subtitle = "Customize your coffee experience", // Changed subtitle
                        onClick = { onNavigate(Screen.CoffeePreferences) }, // Changed navigation target
                        accentColor = Color(0xFFD2B48C) // Coffee-like color
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Button(
                        onClick = {
                            // Implement logout logic
                            // You might want to show a confirmation dialog
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDC143C).copy(alpha = 0.9f)),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Icon(Icons.Default.ExitToApp, contentDescription = null, tint = Color.White)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            "Leave Caffio", // Changed text
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Caffio - Your Coffee Companion", // Changed text
                        color = Color.White.copy(alpha = 0.6f),
                        fontSize = 12.sp,
                        fontStyle = FontStyle.Italic,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
@Composable
fun ProfileOptionCard(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    accentColor: Color = Color(0xFFCD853F) // Coffee-like color
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0x50000000)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(accentColor.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = title, tint = accentColor, modifier = Modifier.size(24.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                Text(text = subtitle, color = Color.White.copy(alpha = 0.7f), fontSize = 13.sp)
            }
            Icon(
                Icons.Default.ArrowForward,
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.5f),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
data class UserModel(val fullName: String, val email: String)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    onBack: () -> Unit,
    onSaveSuccess: (String) -> Unit,
    onSaveError: (String) -> Unit
) {
    var fullName by remember { mutableStateOf("Coffee Lover") } // Changed default name
    var email by remember { mutableStateOf("user@caffio.com") } // Changed default email
    var isLoading by remember { mutableStateOf(false) }
    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF6F4E37), Color(0xFF8B4513), // Coffee-like colors
            Color(0xFFA0522D), Color(0xFFD2B48C)
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Edit Profile",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        containerColor = Color.Transparent
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    Color(0xFFCD853F), Color(0xFFD2B48C), Color(0xFFF5DEB3) // Coffee-like colors
                                )
                            )
                        )
                        .border(3.dp, Color(0xFFA0522D), CircleShape), // Coffee-like color
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = fullName.firstOrNull()?.toString()?.uppercase() ?: "U",
                        color = Color(0xFF6F4E37), fontWeight = FontWeight.Bold, fontSize = 48.sp // Coffee-like color
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { /* TODO: Change profile picture */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFCD853F).copy(alpha = 0.3f)), // Coffee-like color
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Icon(Icons.Default.Edit, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Change Photo", color = Color.White, fontSize = 14.sp)
                }
                Spacer(modifier = Modifier.height(32.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0x40000000)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        OutlinedTextField(
                            value = fullName,
                            onValueChange = { fullName = it },
                            label = { Text("Full Name", color = Color.White.copy(alpha = 0.7f)) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = Color(0xFFCD853F), // Coffee-like color
                                unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                                cursorColor = Color(0xFFCD853F) // Coffee-like color
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = { Text("Email", color = Color.White.copy(alpha = 0.7f)) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = Color(0xFFCD853F), // Coffee-like color
                                unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                                cursorColor = Color(0xFFCD853F) // Coffee-like color
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = {
                        isLoading = true
                        // Simulate an API call or database save
                    },  
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFCD853F)), // Coffee-like color
                    shape = RoundedCornerShape(16.dp),
                    enabled = !isLoading
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    } else {
                        Icon(Icons.Default.AddCircle, contentDescription = null, tint = Color.White)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            "Save Changes",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                // Simulate saving process
                if (isLoading) {
                    LaunchedEffect(key1 = Unit) {
                        delay(2000) // Simulate network delay
                        val success = (0..1).random() == 1 // Simulate success/failure randomly
                        if (success) {
                            onSaveSuccess("Profile updated successfully!")
                        } else {
                            onSaveError("Failed to update profile. Please try again.")
                        }
                        isLoading = false
                    }
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePasswordScreen(
    onBack: () -> Unit,
    onSaveSuccess: (String) -> Unit,
    onSaveError: (String) -> Unit
) {
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF6F4E37), Color(0xFF8B4513), // Coffee-like colors
            Color(0xFFA0522D), Color(0xFFD2B48C)
        )
    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Change Password",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        containerColor = Color.Transparent
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFCD853F).copy(alpha = 0.2f)), // Coffee-like color
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Lock,
                        contentDescription = null,
                        tint = Color(0xFFCD853F), // Coffee-like color
                        modifier = Modifier.size(40.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Secure Your Account",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Update your password to keep your coffee journey safe", // Changed text
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
                Spacer(modifier = Modifier.height(32.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0x40000000)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        OutlinedTextField(
                            value = currentPassword,
                            onValueChange = { currentPassword = it },
                            label = { Text("Current Password", color = Color.White.copy(alpha = 0.7f)) },
                            modifier = Modifier.fillMaxWidth(),
                            visualTransformation = PasswordVisualTransformation(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = Color(0xFFCD853F), // Coffee-like color
                                unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                                cursorColor = Color(0xFFCD853F) // Coffee-like color
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedTextField(
                            value = newPassword,
                            onValueChange = { newPassword = it },
                            label = { Text("New Password", color = Color.White.copy(alpha = 0.7f)) },
                            modifier = Modifier.fillMaxWidth(),
                            visualTransformation = PasswordVisualTransformation(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = Color(0xFFCD853F), // Coffee-like color
                                unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                                cursorColor = Color(0xFFCD853F) // Coffee-like color
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedTextField(
                            value = confirmPassword,
                            onValueChange = { confirmPassword = it },
                            label = { Text("Confirm New Password", color = Color.White.copy(alpha = 0.7f)) },
                            modifier = Modifier.fillMaxWidth(),
                            visualTransformation = PasswordVisualTransformation(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = Color(0xFFCD853F), // Coffee-like color
                                unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                                cursorColor = Color(0xFFCD853F) // Coffee-like color
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = {
                        isLoading = true
                        // Simulate an API call or database save
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFCD853F)), // Coffee-like color
                    shape = RoundedCornerShape(16.dp),
                    enabled = !isLoading
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    } else {
                        Icon(Icons.Default.Lock, contentDescription = null, tint = Color.White)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            "Update Password",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                // Simulate saving process
                if (isLoading) {
                    LaunchedEffect(key1 = Unit) {
                        delay(2000) // Simulate network delay
                        val success = (0..1).random() == 1 // Simulate success/failure randomly
                        if (success) {
                            onSaveSuccess("Password updated successfully!")
                        } else {
                            onSaveError("Failed to update password. Please try again.")
                        }
                        isLoading = false
                    }
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoffeePreferencesScreen(
    onBack: () -> Unit,
    onSaveSuccess: (String) -> Unit,
    onSaveError: (String) -> Unit
) { // Changed function name
    var preferences by remember { mutableStateOf(setOf<String>()) }
    var isLoading by remember { mutableStateOf(false) }
    val coffeeOptions = listOf( // Changed options to coffee-related
        "☕ Espresso" to "Strong & Bold",
        "🥛 Latte" to "Creamy & Smooth",
        "🧊 Iced Coffee" to "Chilled & Refreshing",
        "🍫 Mocha" to "Chocolatey & Indulgent",
        "🫘 Americano" to "Rich & Simple",
        "🍮 Cappuccino" to "Frothy & Classic"
    )
    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF6F4E37), Color(0xFF8B4513), // Coffee-like colors
            Color(0xFFA0522D), Color(0xFFD2B48C)
        )
    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Coffee Preferences", // Changed title
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        containerColor = Color.Transparent
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFD2B48C).copy(alpha = 0.2f)), // Coffee-like color
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "☕", // Changed emoji
                        fontSize = 40.sp
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Choose Your Favorites",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Select the coffee types that match your taste and energy goals", // Changed text
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
                Spacer(modifier = Modifier.height(32.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0x40000000)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        coffeeOptions.forEach { (option, description) ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        preferences = if (preferences.contains(option)) {
                                            preferences - option
                                        } else {
                                            preferences + option
                                        }
                                    }
                                    .padding(vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = preferences.contains(option),
                                    onCheckedChange = { isChecked ->
                                        preferences = if (isChecked) {
                                            preferences + option
                                        } else {
                                            preferences - option
                                        }
                                    },
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = Color(0xFFD2B48C), // Coffee-like color
                                        uncheckedColor = Color.White.copy(alpha = 0.7f),
                                        checkmarkColor = Color(0xFF6F4E37) // Coffee-like color
                                    )
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(text = option, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                                    Text(text = description, color = Color.White.copy(alpha = 0.7f), fontSize = 13.sp)
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = {
                        isLoading = true
                        // Simulate an API call or database save
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD2B48C)), // Coffee-like color
                    shape = RoundedCornerShape(16.dp),
                    enabled = !isLoading
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    } else {
                        Icon(Icons.Default.Favorite, contentDescription = null, tint = Color.White)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            "Save Preferences",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                // Simulate saving process
                if (isLoading) {
                    LaunchedEffect(key1 = Unit) {
                        delay(2000) // Simulate network delay
                        val success = (0..1).random() == 1 // Simulate success/failure randomly
                        if (success) {
                            onSaveSuccess("Coffee preferences saved successfully!")
                        } else {
                            onSaveError("Failed to save preferences. Please try again.")
                        }
                        isLoading = false
                    }
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    CaffioTheme {
        ProfileScreen(onNavigate = {}, onBack = {})
    }
}

@Preview(showBackground = true)
@Composable
fun EditProfileScreenPreview() {
    CaffioTheme {
        EditProfileScreen(onBack = {}, onSaveSuccess = {}, onSaveError = {})
    }
}

@Preview(showBackground = true)
@Composable
fun ChangePasswordScreenPreview() {
    CaffioTheme {
        ChangePasswordScreen(onBack = {}, onSaveSuccess = {}, onSaveError = {})
    }
}

@Preview(showBackground = true)
@Composable
fun CoffeePreferencesScreenPreview() {
    CaffioTheme {
        CoffeePreferencesScreen(onBack = {}, onSaveSuccess = {}, onSaveError = {})
    }
}