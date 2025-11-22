package com.example.roomexample.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.roomexample.R
import com.example.roomexample.domain.Contact
import com.example.roomexample.ui.compose.theme.RoomExampleTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactListScreen(
    contacts: List<Contact>,
    onAddContact: () -> Unit,
    onBack: () -> Unit,
    onContactClick: (Contact) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.title_contact_list)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddContact,
                content = { Icon(Icons.Filled.Add, stringResource(R.string.desc_add_contact)) }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = padding,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(contacts) {
                ContactItem(contact = it, onClick = { onContactClick(it) })
            }
        }
    }

}

@Preview
@Composable
fun ContactListScreenPreview() {
    RoomExampleTheme {
        ContactListScreen(
            contacts = listOf(
                Contact(1, "John Doe", "123-456-7890"),
                Contact(2, "Jane Smith", "098-765-4321")
            ),
            onAddContact = {},
            onBack = {},
            onContactClick = {}
        )
    }
}