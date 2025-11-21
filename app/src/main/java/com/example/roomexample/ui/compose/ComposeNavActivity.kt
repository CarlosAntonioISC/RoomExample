package com.example.roomexample.ui.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.roomexample.ui.compose.theme.RoomExampleTheme

class ComposeNavActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RoomExampleTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ComposeNavigation()
                }
            }
        }
    }

    @Composable
    fun ComposeNavigation() {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "contact_list") {
            composable("contact_list") {
                ContactListScreen(
                    onAddContact = { navController.navigate("contact_detail") },
                    onBack = { finish() }
                )
            }
            composable("contact_detail") {
                ContactDetailScreen(onBack = { navController.popBackStack() })
            }
        }
    }

}