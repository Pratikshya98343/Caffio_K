package com.example.caffio.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.caffio.R
import com.example.caffio.repository.AuthRepositoryImpl
import com.example.caffio.viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoginBody()
        }
    }
}

@Composable
fun LoginBody() {
    val repo = remember { AuthRepositoryImpl(FirebaseAuth.getInstance()) }
    val authViewModel = remember { AuthViewModel(repo) }
    val context = LocalContext.current
    val activity = context as? Activity ?: return

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    // SharedPreferences for "Remember Me"
    val sharedPreferences = context.getSharedPreferences("User", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()

    // Pre-fill credentials if saved
    email = sharedPreferences.getString("email", "") ?: ""
    password = sharedPreferences.getString("password", "") ?: ""

    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // 🔺 Coffee Theme Colors
    val CoffeeBrown = Color(0xFF4B2E2A)
    val Cream = Color(0xFFF5F0E6)
    val Tan = Color(0xFFD7CCC8)
    val SoftGreen = Color(0xFF8DAB95)
    val LightCream = Color(0xFFFAF8F0)

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(LightCream), // Warm cream background
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Demo AlertDialog Button (subtle)
            Button(
                onClick = { showDialog = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Tan,
                    contentColor = CoffeeBrown
                ),
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Info")
            }

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("Welcome Back", color = CoffeeBrown) },
                    text = { Text("Enter your credentials to enjoy your favorite brew.") },
                    confirmButton = {
                        Button(
                            onClick = { showDialog = false },
                            colors = ButtonDefaults.buttonColors(containerColor = SoftGreen)
                        ) {
                            Text("OK")
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = { showDialog = false },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
                        ) {
                            Text("Cancel")
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Brew & Sip",
                style = TextStyle(
                    fontSize = 32.sp,
                    fontFamily = FontFamily.Serif, // Elegant coffee shop font
                    fontWeight = FontWeight.Bold,
                    color = CoffeeBrown
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Sign in to your account",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Tan.copy(alpha = 0.9f)
                )
            )

            Spacer(modifier = Modifier.height(30.dp))

            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "Caffio Logo",
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(200.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Email Field
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp)
                    .testTag("email"),
                placeholder = { Text("Enter email", color = Tan) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Cream.copy(alpha = 0.6f),
                    unfocusedContainerColor = Cream.copy(alpha = 0.4f),
                    focusedIndicatorColor = SoftGreen,
                    unfocusedIndicatorColor = Tan,
                    cursorColor = SoftGreen,
                    focusedLabelColor = CoffeeBrown
                ),
                shape = RoundedCornerShape(16.dp),
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = Tan) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Password Field
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp)
                    .testTag("password"),
                placeholder = { Text("Enter password", color = Tan) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Cream.copy(alpha = 0.6f),
                    unfocusedContainerColor = Cream.copy(alpha = 0.4f),
                    focusedIndicatorColor = SoftGreen,
                    unfocusedIndicatorColor = Tan,
                    cursorColor = SoftGreen,
                    focusedLabelColor = CoffeeBrown
                ),
                shape = RoundedCornerShape(16.dp),
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = Tan) },
                trailingIcon = {
                    Icon(
                        painter = painterResource(
                            if (passwordVisibility) R.drawable.baseline_visibility_24
                            else R.drawable.baseline_visibility_off_24
                        ),
                        contentDescription = null,
                        tint = Tan,
                        modifier = Modifier.clickable { passwordVisibility = !passwordVisibility }
                    )
                },
                visualTransformation = if (passwordVisibility) VisualTransformation.None
                else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            // Remember Me & Forgot Password
            Row(
                modifier = Modifier
                    .padding(horizontal = 25.dp, vertical = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = rememberMe,
                        onCheckedChange = { rememberMe = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = SoftGreen,
                            checkmarkColor = Cream
                        )
                    )
                    Text("Remember me", color = Color.DarkGray)
                }
                Text(
                    "Forgot Password?",
                    color = CoffeeBrown,
                    style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium),
                    modifier = Modifier.clickable {
                        context.startActivity(Intent(context, ForgetPasswordActivity::class.java))
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Login Button
            Button(
                onClick = {
                    if (email.isBlank() || password.isBlank()) {
                        Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    authViewModel.login(email, password) { success, message ->
                        if (success) {
                            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                            if (rememberMe) {
                                editor.putString("email", email)
                                editor.putString("password", password)
                                editor.apply()
                            }
                            val intent = Intent(context, DashboardActivity::class.java)
                            intent.putExtra("email", email)
                            context.startActivity(intent)
                            activity.finish()
                        } else {
                            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp)
                    .height(56.dp)
                    .testTag("submit"),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CoffeeBrown,
                    contentColor = Cream
                )
            ) {
                Text(
                    "Login",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Sign Up Text
            Text(
                text = "Don't have an account? Sign up",
                color = Color(0xFF6B4F4F),
                style = TextStyle(fontSize = 15.sp),
                modifier = Modifier.clickable {
                    val intent = Intent(context, RegistrationActivity::class.java)
                    context.startActivity(intent)
                    activity.finish()
                }
            )

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}