package com.example.roomexample.ui.compose

import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.roomexample.R
import com.example.roomexample.ui.compose.theme.RoomExampleTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactListScreen(
    onAddContact: () -> Unit,
    onBack: () -> Unit
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
        LazyRow(contentPadding = padding) {

        }
    }

}

@Preview
@Composable
fun ContactListScreenPreview() {
    RoomExampleTheme {
        ContactListScreen(
            onAddContact = {},
            onBack = {}
        )
    }
}