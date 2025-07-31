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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.example.caffio.R
import com.example.caffio.model.UserModel
import com.example.caffio.repository.AuthRepositoryImpl
import com.example.caffio.repository.UserRepositoryImpl
import com.example.caffio.viewmodel.AuthViewModel
import com.example.caffio.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth

class RegistrationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Scaffold { innerPadding ->
                RegBody(innerPadding)
            }
        }
    }
}

@Composable
fun RegBody(innerPaddingValues: PaddingValues) {
    val repo = remember { AuthRepositoryImpl(FirebaseAuth.getInstance()) }
    val authViewModel = remember { AuthViewModel(repo) }

    val userRepo = remember { UserRepositoryImpl() }
    val userViewModel = remember { UserViewModel(userRepo) }


    val context = LocalContext.current
    val activity= context as? Activity

    var firstName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var lastname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    var selectedOptionText by remember { mutableStateOf("Select Option") }

    val options = listOf("Nepal", "India", "China")

    var textFieldSize by remember { mutableStateOf(Size.Zero) } // to capture textfield size
    Column(
        modifier = Modifier
            .padding(innerPaddingValues).padding(horizontal = 10.dp)
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Text(
            text = "Hello there!",
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(
                fontSize = 30.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Create an account",
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(
                fontSize = 22.sp
            )
        )
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )

        Spacer(modifier = Modifier.height(50.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = firstName,
                onValueChange = {
                    firstName = it
                },
                placeholder = {
                    Text("Firstname")
                },
                modifier = Modifier.weight(1f).testTag("firstname")
            )
            Spacer(modifier = Modifier.width(10.dp))
            OutlinedTextField(
                value = lastname,
                onValueChange = {
                    lastname = it
                },
                placeholder = {
                    Text("Lastname")
                },
                modifier = Modifier.weight(1f).testTag("lastname"),

                )
        }
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            placeholder = {
                Text("abc@gmail.com")
            },
            modifier = Modifier.fillMaxWidth().testTag("email")
        )

        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()) {
            OutlinedTextField(
                value = selectedOptionText,
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        // capture the size of the TextField
                        textFieldSize = coordinates.size.toSize()
                    }
                    .clickable { expanded = true }
                    .testTag("country"),
                placeholder = { Text("Select Country") },
                enabled = false, // prevent manual typing
                colors = TextFieldDefaults.colors(
                    disabledIndicatorColor = Color.Gray,
                    disabledContainerColor = Color.White,
                ),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null
                    )
                }
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            selectedOptionText = option
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            placeholder = {
                Text("*******")
            },
            modifier = Modifier.fillMaxWidth().testTag("password")
        )
        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            authViewModel.register(email,password){
                    success, message, userId ->
                if(success){
                    var userModel = UserModel(userId,email,firstName,lastname, "Male",
                        "9851009530",selectedOptionText
                    )
                    userViewModel.addUserToDatabase(userId,userModel){
                            success , message ->
                        if (success){
                            Toast.makeText(context,message, Toast.LENGTH_LONG).show()
                        }else {
                            Toast.makeText(context,message, Toast.LENGTH_LONG).show()
                        }

                    }
                }else{
                    Toast.makeText(context,message, Toast.LENGTH_LONG).show()
                }
            }
        },
            modifier = Modifier.fillMaxWidth().testTag("registerBtn"),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4B2E2A),
                contentColor = Color.Black
            )
        ) {

            Text("Register")
        }
        Spacer(modifier = Modifier.height(10.dp))

        Row(modifier = Modifier
            .fillMaxSize(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Already have an account? ",
                fontSize = 15.sp)
            Text(
                text = "Login",
                fontSize = 15.sp,
                textDecoration = TextDecoration.Underline,
                color = Color(0xFF4B2E2A),
                modifier = Modifier.clickable {

                    val intent = Intent(context, LoginActivity::class.java)
                    context.startActivity(intent)

                    //to destroy activity
                    activity?.finish()

                }
            )
        }

    }
}

@Preview
@Composable
fun RegPreview() {
    RegBody(innerPaddingValues = PaddingValues(0.dp))
}