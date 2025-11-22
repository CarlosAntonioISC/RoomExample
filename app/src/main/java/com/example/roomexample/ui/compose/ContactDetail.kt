package com.example.roomexample.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.roomexample.R
import com.example.roomexample.domain.Contact
import com.example.roomexample.ui.ContactDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactDetailScreen(
    viewModel: ContactDetailViewModel,
    onBack: () -> Unit,
    onDeleted: () -> Unit
) {
    val contact by viewModel.contact.collectAsState()
    var showEditSheet by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.title_contact_detail)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { showEditSheet = contact != null },
                        enabled = contact != null
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = stringResource(R.string.action_edit_contact)
                        )
                    }
                    IconButton(
                        onClick = { viewModel.deleteContact(onDeleted) },
                        enabled = contact != null
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(R.string.action_delete_contact)
                        )
                    }
                }
            )
        }
    ) { padding ->
        ContactDetailContent(
            contact = contact,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        )
    }

    if (showEditSheet && contact != null) {
        AddContactBottomSheet(
            initialName = contact!!.name,
            initialPhone = contact!!.phone,
            onConfirm = { name, phone ->
                viewModel.updateContact(name, phone)
                showEditSheet = false
            },
            onDismiss = { showEditSheet = false }
        )
    }
}

@Composable
private fun ContactDetailContent(contact: Contact?, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (contact == null) {
            Text(
                text = stringResource(R.string.message_contact_not_found),
                style = MaterialTheme.typography.bodyMedium
            )
            return
        }

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = contact.name,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = contact.phone,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
