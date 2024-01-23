package com.ozanergun.hw2.db

import androidx.room.*

@Dao
interface ContactDao {

    @Insert
    fun createContact(contact: Contact)

    @Query("SELECT * FROM contactTable ORDER BY full_name ASC")
    fun readAllContacts(): MutableList<Contact>

    @Update
    fun updateContact(contact: Contact)

    @Delete
    fun deleteContact(contact: Contact)
}