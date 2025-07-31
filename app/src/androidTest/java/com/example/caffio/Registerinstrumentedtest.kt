package com.example.caffio

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.caffio.view.RegistrationActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegisterInstrumentedTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<RegistrationActivity>()

    @Test
    fun testSuccessfulRegistration() {
        // Fill in first name
        composeRule.onNodeWithTag("firstname")
            .performTextInput("Test")

        // Fill in last name
        composeRule.onNodeWithTag("lastname")
            .performTextInput("User")

        // Fill in email (use random to avoid duplicate email error)
        val uniqueEmail = "testuser${System.currentTimeMillis()}@example.com"
        composeRule.onNodeWithTag("email")
            .performTextInput(uniqueEmail)

        // Fill in password
        composeRule.onNodeWithTag("password")
            .performTextInput("test1234")

        // Select country (just open dropdown and click first option)
        composeRule.onNodeWithTag("country")
            .performClick()

        // Wait for dropdown to render and click first item (Nepal)
        composeRule.waitForIdle() // Ensure dropdown is shown
        composeRule.onNodeWithTag("country") // No test tag on menu item, assume default
            .performClick()

        // Click Register button
        composeRule.onNodeWithTag("registerBtn")
            .performClick()

        // Wait for everything to complete (e.g., database write)
        composeRule.waitForIdle()
    }
}