package com.example.roomexample.ui.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
        var showAddContactSheet by rememberSaveable { mutableStateOf(false) }

        Box(modifier = Modifier.fillMaxSize()) {
            NavHost(navController = navController, startDestination = "contact_list") {
                composable("contact_list") {
                    ContactListScreen(
                        onAddContact = { showAddContactSheet = true },
                        onBack = { finish() }
                    )
                }
                composable("contact_detail") {
                    ContactDetailScreen(onBack = { navController.popBackStack() })
                }
            }

            if (showAddContactSheet) {
                AddContactBottomSheet(
                    onConfirm = { _, _ -> showAddContactSheet = false },
                    onDismiss = { showAddContactSheet = false }
                )
            }
        }
    }

}