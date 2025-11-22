package com.example.roomexample.data

import com.example.roomexample.data.dao.ContactDao
import com.example.roomexample.data.entity.ContactEntity
import com.example.roomexample.domain.Contact
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ContactRepository(
    private val contactDao: ContactDao
) {

    fun getAllContacts(): Flow<List<Contact>> {
        return contactDao.getAllContacts().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    fun getContactById(id: Int): Flow<Contact> {
        return contactDao.getContactById(id).map { it.toDomain() }
    }

    suspend fun insertContact(contact: Contact) {
        contactDao.insert(contact.toEntity())
    }

    suspend fun updateContact(contact: Contact) {
        contactDao.update(contact.toEntity())
    }

    suspend fun deleteContact(contact: Contact) {
        contactDao.delete(contact.toEntity())
    }
}

fun ContactEntity.toDomain() = Contact(id, name, phone)

fun Contact.toEntity() = ContactEntity(id, name, phone)
