package com.example.roomexample.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomexample.data.ContactRepository
import com.example.roomexample.domain.Contact
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ContactViewModel(
    private val repository: ContactRepository
): ViewModel() {

    val contacts = repository
        .getAllContacts()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            emptyList()
        )

    fun addContact(contact: Contact) {
        viewModelScope.launch {
            repository.insertContact(contact)
        }
    }
}