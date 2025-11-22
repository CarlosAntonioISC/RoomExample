package com.example.roomexample.ui.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.roomexample.data.AppDatabase
import com.example.roomexample.data.ContactRepository
import com.example.roomexample.domain.Contact
import com.example.roomexample.ui.ContactDetailViewModel
import com.example.roomexample.ui.ContactDetailViewModelFactory
import com.example.roomexample.ui.ContactViewModel
import com.example.roomexample.ui.ContactViewModelFactory
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
        val repository = ContactRepository(AppDatabase.getDatabase(applicationContext).contactDao())
        val viewModel = ViewModelProvider(this, ContactViewModelFactory(repository))[ContactViewModel::class.java]

        Box(modifier = Modifier.fillMaxSize()) {
            NavHost(navController = navController, startDestination = "contact_list") {
                composable("contact_list") {
                    val contacts by viewModel.contacts.collectAsState()
                    ContactListScreen(
                        contacts = contacts,
                        onAddContact = { showAddContactSheet = true },
                        onBack = { finish() },
                        onContactClick = { navController.navigate("contact_detail/${it.id}") }
                    )
                }
                composable(
                    route = "contact_detail/{contactId}",
                    arguments = listOf(navArgument("contactId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val contactId = backStackEntry.arguments?.getInt("contactId") ?: 0
                    val detailViewModel: ContactDetailViewModel = viewModel(
                        factory = ContactDetailViewModelFactory(repository, contactId)
                    )
                    ContactDetailScreen(
                        viewModel = detailViewModel,
                        onBack = { navController.popBackStack() },
                        onDeleted = { navController.popBackStack() }
                    )
                }
            }

            if (showAddContactSheet) {
                AddContactBottomSheet(
                    onConfirm = { name, phone ->
                        viewModel.addContact(Contact(id = 0, name = name, phone = phone))
                        showAddContactSheet = false
                    },
                    onDismiss = { showAddContactSheet = false }
                )
            }
        }
    }

}