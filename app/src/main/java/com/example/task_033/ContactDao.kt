package com.example.task_033

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Delete
import androidx.room.Query

@Dao
interface ContactDao {
    @Insert
    suspend fun insert(contact: Contact)

    @Delete
    suspend fun delete(contact: Contact)

    @Query("SELECT * FROM contacts_table ORDER BY id ASC")
    fun getAllContacts(): List<Contact>

    @Query("DELETE FROM contacts_table")
    fun deleteAll()
}