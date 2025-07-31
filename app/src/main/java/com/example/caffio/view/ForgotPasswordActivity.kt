package com.example.caffio.view

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.caffio.R
import com.example.caffio.repository.UserRepositoryImpl
import com.example.caffio.viewmodel.UserViewModel

class ForgetPasswordActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            forgetBody()
        }
    }
}

@Composable
fun forgetBody() {
    val repo = remember { UserRepositoryImpl() }
    val userViewModel = remember { UserViewModel(repo) }

    val context = LocalContext.current
    val activity = context as Activity

    var email by remember { mutableStateOf("") }

    // 🔺 Coffee Theme Colors
    val CoffeeBrown = Color(0xFF4B2E2A)
    val Cream = Color(0xFFF5F0E6)
    val Tan = Color(0xFFD7CCC8)
    val coffeeBrown = Color(0xFF4B2E2A)
    val LightCream = Color(0xFFFAF8F0)

    Scaffold(
        modifier = Modifier.background(LightCream)
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Forgot Password?",
                style = TextStyle(
                    fontSize = 32.sp,
                    fontFamily = FontFamily.Serif, // Elegant, coffee-shop style
                    fontWeight = FontWeight.Bold,
                    color = CoffeeBrown
                ),
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Enter your email to reset your password",
                style = TextStyle(
                    fontSize = 18.sp,
                    color = Tan
                ),
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(30.dp))

            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "Coffee Logo",
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(200.dp)
            )

            Spacer(modifier = Modifier.height(50.dp))

            // Email Input Field
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = {
                    Text("abc@gmail.com", color = Tan)
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.baseline_email_24),
                        contentDescription = null,
                        tint = Tan
                    )
                },
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Cream.copy(alpha = 0.6f),
                    unfocusedContainerColor = Cream.copy(alpha = 0.4f),
                    focusedIndicatorColor = coffeeBrown,
                    unfocusedIndicatorColor = Tan,
                    cursorColor = coffeeBrown,
                    focusedLabelColor = CoffeeBrown
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Submit Button
            Button(
                onClick = {
                    if (email.isBlank()) {
                        Toast.makeText(context, "Please enter your email", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    userViewModel.forgetPassword(email) { success, message ->
                        if (success) {
                            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                            activity.finish()
                        } else {
                            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                        }
                    }
                },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CoffeeBrown,
                    contentColor = Cream
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(56.dp)
            ) {
                Text(
                    text = "Send Reset Link",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Back to Login Hint
            Text(
                text = "Remember your password? Sign in",
                color = coffeeBrown,
                style = TextStyle(fontSize = 15.sp),
                modifier = Modifier.clickable {
                    activity.finish() // Goes back to login
                }
            )
        }
    }
}

@Preview
@Composable
fun prev() {
    forgetBody()
}