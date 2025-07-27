package com.example.caffio.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.caffio.R
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SplashBody()
        }
    }
}

@Composable
fun SplashBody() {
    val context = LocalContext.current
    val activity = context as? Activity

    // Animation states
    val logoScale = remember { Animatable(0.6f) }
    val logoAlpha = remember { Animatable(0f) }
    val titleAlpha = remember { Animatable(0f) }
    val titleOffset = remember { Animatable(30f) }
    val subtitleAlpha = remember { Animatable(0f) }
    val buttonAlpha = remember { Animatable(0f) }
    val buttonScale = remember { Animatable(0.9f) }
    val backgroundAlpha = remember { Animatable(0f) }
    val beansAlpha = remember { Animatable(0f) }

    // Infinite animations
    val infiniteTransition = rememberInfiniteTransition(label = "infinite")

    val beansRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = EaseInOutCubic)
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

    // Warm coffee shop color palette
    val darkBrown = Color(0xFF2D1B14)
    val richBrown = Color(0xFF3C2414)
    val coffeeBrown = Color(0xFF6B4226)
    val warmOrange = Color(0xFFD2691E)
    val creamyBeige = Color(0xFFF5E6D3)
    val warmWhite = Color(0xFFFFF8F0)
    val caramel = Color(0xFFB8860B)
    val milkFoam = Color(0xFFFAF0E6)
    val lightBrown = Color(0xFF8B4513)

    fun navigateToLogin() {
        val intent = Intent(context, LoginActivity::class.java)
        context.startActivity(intent)
        activity?.finish()
    }

    // Warm animation sequence
    LaunchedEffect(Unit) {
        // Background warmth
        backgroundAlpha.animateTo(1f, tween(1000))

        delay(200)

        // Coffee beans floating
        beansAlpha.animateTo(1f, tween(1500))

        delay(400)

        // Logo with gentle entrance
        logoAlpha.animateTo(1f, tween(1200, easing = EaseOutCubic))
        logoScale.animateTo(1f, tween(1200, easing = EaseOutCubic))

        delay(500)

        // Title slide up
        titleAlpha.animateTo(1f, tween(800))
        titleOffset.animateTo(0f, tween(800, easing = EaseOutCubic))

        delay(300)

        // Subtitle fade
        subtitleAlpha.animateTo(1f, tween(600))

        delay(500)

        // Button entrance
        buttonAlpha.animateTo(1f, tween(600))
        buttonScale.animateTo(1f, tween(600, easing = EaseOutCubic))
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = darkBrown
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Background image
            Image(
                painter = painterResource(id = R.drawable.background), // Replace with your background image
                contentDescription = "Coffee Background",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(backgroundAlpha.value)
            )

            // Overlay for better text visibility
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                darkBrown.copy(alpha = 0.4f),
                                richBrown.copy(alpha = 0.6f),
                                coffeeBrown.copy(alpha = 0.5f),
                                darkBrown.copy(alpha = 0.7f)
                            )
                        )
                    )
                    .alpha(backgroundAlpha.value)
            )

            // Floating coffee beans animation
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(beansAlpha.value)
            ) {
                val width = size.width
                val height = size.height

                // Warm ambient lighting
                drawRect(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            warmOrange.copy(alpha = warmGlow * 0.15f),
                            caramel.copy(alpha = warmGlow * 0.08f),
                            Color.Transparent
                        ),
                        center = Offset(width * 0.3f, height * 0.2f),
                        radius = width * 0.5f
                    )
                )

                // Second warm glow
                drawRect(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            warmOrange.copy(alpha = warmGlow * 0.1f),
                            Color.Transparent
                        ),
                        center = Offset(width * 0.8f, height * 0.8f),
                        radius = width * 0.4f
                    )
                )

                // Floating coffee beans
                rotate(beansRotation) {
                    for (i in 0..15) {
                        val angle = i * 24f
                        val radius = 120f + (i % 3) * 40f
                        val x = width * 0.5f + cos(Math.toRadians(angle.toDouble())).toFloat() * radius
                        val y = height * 0.5f + sin(Math.toRadians(angle.toDouble())).toFloat() * radius

                        // Coffee bean shape
                        val beanSize = 4f + (i % 3) * 2f
                        val alpha = (0.2f + (sin(beansRotation * 0.05f + i) * 0.1f)) * beansAlpha.value

                        // Bean body
                        drawCircle(
                            color = coffeeBrown.copy(alpha = alpha),
                            radius = beanSize,
                            center = Offset(x, y)
                        )

                        // Bean line
                        drawLine(
                            color = lightBrown.copy(alpha = alpha),
                            start = Offset(x - beanSize * 0.6f, y),
                            end = Offset(x + beanSize * 0.6f, y),
                            strokeWidth = 1f
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Coffee shop logo
                Image(
                    painter = painterResource(id = R.drawable.logo), // Replace with your logo
                    contentDescription = "Caffio Logo",
                    modifier = Modifier
                        .size(180.dp)
                        .scale(logoScale.value)
                        .alpha(logoAlpha.value)
                )

                Spacer(modifier = Modifier.height(50.dp))

                // Warm coffee shop branding
                Text(
                    text = "CAFFIO",
                    fontSize = 44.sp,
                    fontWeight = FontWeight.Bold,
                    color = creamyBeige,
                    letterSpacing = 4.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .alpha(titleAlpha.value)
                        .offset(y = titleOffset.value.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Decorative coffee line
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .height(2.dp)
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    warmOrange.copy(alpha = 0.8f),
                                    caramel,
                                    warmOrange.copy(alpha = 0.8f),
                                    Color.Transparent
                                )
                            )
                        )
                        .alpha(titleAlpha.value)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Crafted with Love & Passion",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = milkFoam.copy(alpha = 0.9f),
                    letterSpacing = 1.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.alpha(subtitleAlpha.value)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Fresh • Local • Artisanal",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light,
                    color = creamyBeige.copy(alpha = 0.7f),
                    letterSpacing = 0.8.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.alpha(subtitleAlpha.value)
                )

                Spacer(modifier = Modifier.height(60.dp))

                // Warm coffee shop button
                Button(
                    onClick = { navigateToLogin() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .scale(buttonScale.value)
                        .alpha(buttonAlpha.value),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(30.dp)
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
                                shape = RoundedCornerShape(30.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Start Your Coffee Journey",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = warmWhite,
                            letterSpacing = 0.8.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                // Coffee shop footer
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.alpha(buttonAlpha.value)
                ) {
                    // Coffee bean dots
                    repeat(3) { index ->
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .background(
                                    color = when (index) {
                                        0 -> warmOrange.copy(alpha = 0.8f)
                                        1 -> caramel.copy(alpha = 0.8f)
                                        else -> creamyBeige.copy(alpha = 0.8f)
                                    },
                                    shape = CircleShape
                                )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Where Every Cup Tells a Story",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light,
                    color = creamyBeige.copy(alpha = 0.6f),
                    letterSpacing = 0.5.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.alpha(buttonAlpha.value)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSplash() {
    SplashBody()
}