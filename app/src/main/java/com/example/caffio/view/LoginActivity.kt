package com.example.caffio.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.caffio.R
import com.example.caffio.repository.UserRepositoryImpl
import com.example.caffio.viewmodel.UserViewModel
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoginBody1()
        }
    }
}

@Composable
fun LoginBody1() {
    val repo = remember { UserRepositoryImpl() }
    val userViewModel = remember { UserViewModel(repo) }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val activity = context as Activity

    val sharedPreferences = context.getSharedPreferences("User", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()

    val localEmail: String = sharedPreferences.getString("email", "").toString()
    val localPassword: String = sharedPreferences.getString("password", "").toString()

    email = localEmail
    password = localPassword

    val couroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var showDialog by remember { mutableStateOf(false) }

    // Coffee theme colors
    val darkBrown = Color(0xFF2D1B14)
    val richBrown = Color(0xFF3C2414)
    val coffeeBrown = Color(0xFF6B4226)
    val warmOrange = Color(0xFFD2691E)
    val creamyBeige = Color(0xFFF5E6D3)
    val warmWhite = Color(0xFFFFF8F0)
    val espresso = Color(0xFF3C2415)
    val caramel = Color(0xFFB8860B)
    val milkFoam = Color(0xFFFAF0E6)
    val lightBrown = Color(0xFF8B4513)
    val cardBg = Color(0xFFFFFAF0)

    // Animation states
    val cardAlpha = remember { Animatable(0f) }
    val logoAlpha = remember { Animatable(0f) }
    val titleAlpha = remember { Animatable(0f) }
    val beansAlpha = remember { Animatable(0f) }

    // Infinite animations
    val infiniteTransition = rememberInfiniteTransition(label = "infinite")

    val beansRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(30000, easing = EaseInOutCubic)
        ),
        label = "beansRotation"
    )

    val warmGlow by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.6f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = EaseInOutCubic)
        ),
        label = "warmGlow"
    )

    // Animation sequence
    LaunchedEffect(Unit) {
        delay(200)
        beansAlpha.animateTo(1f, tween(1000))

        delay(300)
        logoAlpha.animateTo(1f, tween(800))

        delay(200)
        titleAlpha.animateTo(1f, tween(600))

        delay(300)
        cardAlpha.animateTo(1f, tween(800))
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = darkBrown
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Animated coffee background
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(beansAlpha.value)
            ) {
                val width = size.width
                val height = size.height

                // Coffee background gradient
                drawRect(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            darkBrown,
                            richBrown,
                            coffeeBrown.copy(alpha = 0.8f),
                            darkBrown
                        )
                    )
                )

                // Warm ambient lighting
                drawRect(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            warmOrange.copy(alpha = warmGlow * 0.1f),
                            caramel.copy(alpha = warmGlow * 0.05f),
                            Color.Transparent
                        ),
                        center = Offset(width * 0.2f, height * 0.1f),
                        radius = width * 0.4f
                    )
                )

                // Second warm glow
                drawRect(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            warmOrange.copy(alpha = warmGlow * 0.08f),
                            Color.Transparent
                        ),
                        center = Offset(width * 0.8f, height * 0.9f),
                        radius = width * 0.3f
                    )
                )

                // Floating coffee beans
                rotate(beansRotation) {
                    for (i in 0..20) {
                        val angle = i * 18f
                        val radius = 100f + (i % 4) * 30f
                        val x = width * 0.5f + cos(Math.toRadians(angle.toDouble())).toFloat() * radius
                        val y = height * 0.5f + sin(Math.toRadians(angle.toDouble())).toFloat() * radius

                        val beanSize = 3f + (i % 3) * 1.5f
                        val alpha = (0.1f + (sin(beansRotation * 0.03f + i) * 0.05f)) * beansAlpha.value

                        // Coffee bean
                        drawCircle(
                            color = coffeeBrown.copy(alpha = alpha),
                            radius = beanSize,
                            center = Offset(x, y)
                        )

                        // Bean line
                        drawLine(
                            color = lightBrown.copy(alpha = alpha),
                            start = Offset(x - beanSize * 0.5f, y),
                            end = Offset(x + beanSize * 0.5f, y),
                            strokeWidth = 0.8f
                        )
                    }
                }
            }

            Scaffold(
                snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                containerColor = Color.Transparent
            ) { padding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(horizontal = 24.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.height(40.dp))

                    // Logo section with warm glow
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .padding(bottom = 24.dp)
                            .alpha(logoAlpha.value)
                    ) {
                        // Warm glow background
                        Box(
                            modifier = Modifier
                                .size(140.dp)
                                .background(
                                    brush = Brush.radialGradient(
                                        colors = listOf(
                                            warmOrange.copy(alpha = 0.15f),
                                            caramel.copy(alpha = 0.08f),
                                            Color.Transparent
                                        )
                                    ),
                                    shape = CircleShape
                                )
                        )

                        // Logo background
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .shadow(12.dp, CircleShape)
                                .background(
                                    brush = Brush.radialGradient(
                                        colors = listOf(
                                            creamyBeige,
                                            warmWhite.copy(alpha = 0.95f),
                                            milkFoam.copy(alpha = 0.9f)
                                        )
                                    ),
                                    shape = CircleShape
                                )
                        )

                        Icon(
                            painter = painterResource(R.drawable.logo),
                            contentDescription = "logo",
                            modifier = Modifier.size(60.dp),
                            tint = espresso
                        )
                    }

                    // Title section
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.alpha(titleAlpha.value)
                    ) {
                        Text(
                            text = "CAFFIO",
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold,
                            color = creamyBeige,
                            textAlign = TextAlign.Center,
                            letterSpacing = 3.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Coffee bean decoration
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            repeat(3) { index ->
                                Box(
                                    modifier = Modifier
                                        .size(4.dp)
                                        .background(
                                            color = when (index) {
                                                0 -> warmOrange
                                                1 -> caramel
                                                else -> creamyBeige
                                            },
                                            shape = CircleShape
                                        )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Welcome Back!",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = milkFoam,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Sign in to continue your coffee journey",
                            fontSize = 14.sp,
                            color = creamyBeige.copy(alpha = 0.8f),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // Login Card with coffee theme
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .alpha(cardAlpha.value)
                            .shadow(
                                elevation = 20.dp,
                                shape = RoundedCornerShape(28.dp),
                                ambientColor = warmOrange.copy(alpha = 0.2f),
                                spotColor = caramel.copy(alpha = 0.3f)
                            ),
                        shape = RoundedCornerShape(28.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = cardBg
                        )
                    ) {
                        // Card gradient overlay
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            warmWhite.copy(alpha = 0.95f),
                                            milkFoam.copy(alpha = 0.9f),
                                            creamyBeige.copy(alpha = 0.85f)
                                        )
                                    )
                                )
                        ) {
                            Column(
                                modifier = Modifier.padding(32.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                // Coffee cup icon
                                Box(
                                    modifier = Modifier
                                        .size(70.dp)
                                        .background(
                                            brush = Brush.radialGradient(
                                                colors = listOf(
                                                    warmOrange.copy(alpha = 0.1f),
                                                    caramel.copy(alpha = 0.05f),
                                                    Color.Transparent
                                                )
                                            ),
                                            shape = CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.logo),
                                        contentDescription = null,
                                        modifier = Modifier.size(40.dp),
                                        tint = espresso
                                    )
                                }

                                Spacer(modifier = Modifier.height(28.dp))

                                // Email Field
                                OutlinedTextField(
                                    value = email,
                                    onValueChange = { email = it },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .shadow(4.dp, RoundedCornerShape(20.dp)),
                                    placeholder = {
                                        Text(
                                            text = "Enter your email",
                                            color = espresso.copy(alpha = 0.6f)
                                        )
                                    },
                                    colors = TextFieldDefaults.colors(
                                        focusedContainerColor = warmWhite,
                                        unfocusedContainerColor = milkFoam,
                                        focusedIndicatorColor = warmOrange,
                                        unfocusedIndicatorColor = Color.Transparent,
                                        cursorColor = espresso,
                                        focusedLabelColor = warmOrange,
                                        focusedTextColor = espresso,
                                        unfocusedTextColor = espresso
                                    ),
                                    shape = RoundedCornerShape(20.dp),
                                    leadingIcon = {
                                        Icon(
                                            Icons.Default.Email,
                                            contentDescription = null,
                                            tint = warmOrange
                                        )
                                    },
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Email
                                    )
                                )

                                Spacer(modifier = Modifier.height(20.dp))

                                // Password Field
                                OutlinedTextField(
                                    value = password,
                                    onValueChange = { password = it },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .shadow(4.dp, RoundedCornerShape(20.dp)),
                                    placeholder = {
                                        Text(
                                            text = "Enter your password",
                                            color = espresso.copy(alpha = 0.6f)
                                        )
                                    },
                                    colors = TextFieldDefaults.colors(
                                        focusedContainerColor = warmWhite,
                                        unfocusedContainerColor = milkFoam,
                                        focusedIndicatorColor = warmOrange,
                                        unfocusedIndicatorColor = Color.Transparent,
                                        cursorColor = espresso,
                                        focusedLabelColor = warmOrange,
                                        focusedTextColor = espresso,
                                        unfocusedTextColor = espresso
                                    ),
                                    shape = RoundedCornerShape(20.dp),
                                    leadingIcon = {
                                        Icon(
                                            Icons.Default.Lock,
                                            contentDescription = null,
                                            tint = warmOrange
                                        )
                                    },
                                    trailingIcon = {
                                        Icon(
                                            painterResource(
                                                if (passwordVisibility) R.drawable.baseline_visibility_24 else
                                                    R.drawable.baseline_visibility_off_24
                                            ),
                                            contentDescription = null,
                                            tint = warmOrange,
                                            modifier = Modifier.clickable {
                                                passwordVisibility = !passwordVisibility
                                            }
                                        )
                                    },
                                    visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Password
                                    )
                                )

                                Spacer(modifier = Modifier.height(24.dp))

                                // Remember Me and Forgot Password
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Checkbox(
                                            colors = CheckboxDefaults.colors(
                                                checkedColor = warmOrange,
                                                checkmarkColor = warmWhite,
                                                uncheckedColor = espresso.copy(alpha = 0.6f)
                                            ),
                                            checked = rememberMe,
                                            onCheckedChange = { rememberMe = it }
                                        )
                                        Text(
                                            "Remember me",
                                            color = espresso,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }

                                    Text(
                                        "Forgot Password?",
                                        color = warmOrange,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier.clickable { /* Handle forgot password */ }
                                    )
                                }

                                Spacer(modifier = Modifier.height(32.dp))

                                // Login Button
                                Button(
                                    onClick = {
                                        if (rememberMe) {
                                            editor.putString("email", email)
                                            editor.putString("password", password)
                                            editor.apply()
                                        }

                                        userViewModel.login(email, password) { success, message ->
                                            if (success) {
                                                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                                val intent = Intent(context, DashboardActivity::class.java)
                                                context.startActivity(intent)
                                                activity.finish()
                                            } else {
                                                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                            }
                                        }
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(60.dp)
                                        .shadow(8.dp, RoundedCornerShape(20.dp)),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.Transparent
                                    ),
                                    shape = RoundedCornerShape(20.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(
                                                brush = Brush.horizontalGradient(
                                                    colors = listOf(
                                                        warmOrange,
                                                        caramel,
                                                        warmOrange
                                                    )
                                                ),
                                                shape = RoundedCornerShape(20.dp)
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            "Sign In",
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = warmWhite,
                                            letterSpacing = 1.sp
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // Sign Up Link
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .alpha(cardAlpha.value)
                            .clickable {
                                val intent = Intent(context, RegistrationActivity::class.java)
                                context.startActivity(intent)
                                activity.finish()
                            },
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    brush = Brush.horizontalGradient(
                                        colors = listOf(
                                            warmOrange.copy(alpha = 0.1f),
                                            caramel.copy(alpha = 0.05f),
                                            warmOrange.copy(alpha = 0.1f)
                                        )
                                    ),
                                    shape = RoundedCornerShape(16.dp)
                                )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    "Don't have an account? ",
                                    color = creamyBeige.copy(alpha = 0.8f),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    "Sign Up",
                                    color = warmOrange,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(40.dp))
                }
            }

            // Alert Dialog
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    confirmButton = {
                        Button(
                            onClick = { showDialog = false },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = warmOrange
                            ),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text("OK", fontWeight = FontWeight.SemiBold, color = warmWhite)
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = { showDialog = false },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Gray
                            ),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text("Cancel", fontWeight = FontWeight.SemiBold, color = warmWhite)
                        }
                    },
                    title = {
                        Text(
                            text = "Alert Title",
                            fontWeight = FontWeight.Bold,
                            color = espresso
                        )
                    },
                    text = {
                        Text(
                            "This is an alert dialog message.",
                            color = espresso
                        )
                    },
                    containerColor = cardBg,
                    shape = RoundedCornerShape(24.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewLogin() {
    LoginBody1()
}