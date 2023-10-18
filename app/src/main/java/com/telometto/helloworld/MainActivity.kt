package com.telometto.helloworld

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * The "MainActivity" class responsible for managing the application's UI.
 */
class MainActivity : ComponentActivity() {

    /**
     * Called when the activity is first created.
     * @param savedInstanceState Bundle containing the activity's previous state, if any.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController() // Initializes the navigation controller

            // Set up navigation destinations
            NavHost(navController, startDestination = "login") {

                // First screen
                composable("login") {
                    LoginScreen(navController)
                }

                // Second screen; input gets passed from first to second screen
                composable("secondScreen/{name}/{country}") { backStackEntry ->
                    val name = backStackEntry.arguments?.getString("name")
                    val country = backStackEntry.arguments?.getString("country")

                    SecondScreen(name = name, country = country)
                }
            }
        }
    }
}

/**
 * Composable function for rendering the login screen.
 * @param navController Navigation controller that handles the screen transitions.
 */
@Composable
fun LoginScreen(navController: NavHostController) {
    // Keeps track of the variables in the various input fields
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }

    // Styling of the text
    val inputStyle = LocalTextStyle.current.copy(
        color = Color.White,
        fontFamily = FontFamily.Default
    )

    // Layout
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // Name field
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(text = "Name") },
            placeholder = { Text(text = "Please enter your name") },
            textStyle = inputStyle
        )

        // Space between input fields
        Spacer(Modifier.height(16.dp))

        // Age field
        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            label = { Text(text = "Age") },
            placeholder = { Text(text = "Please enter your age") },
            textStyle = inputStyle
        )

        Spacer(Modifier.height(16.dp))

        // Country field
        OutlinedTextField(
            value = country,
            onValueChange = { country = it },
            label = { Text(text = "Country") },
            placeholder = { Text(text = "Please enter your country") },
            textStyle = inputStyle
        )

        Spacer(Modifier.height(16.dp))

        // Button to log in
        Button(
            // Sends user to second screen, while remembering name, upon clicking
            onClick = { navController.navigate("secondScreen/$name/$country") }
        ) {
            Text("Login")
        }
    }
}

/**
 * Composable function for rendering the second screen.
 * @param name The name and country are passed from the login screen, if any.
 */
@Composable
fun SecondScreen(name: String?, country: String?) {

    // Today's date in ISO 8601 format (implemented with "normal" Java code)
    val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

    // Layout of second screen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Hello, $name from $country!", color = Color.White)
        Text("Today's date is: $date", color = Color.White)
    }
}