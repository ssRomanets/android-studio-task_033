package com.example.task_033

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts_table")
data class Contact(
    @ColumnInfo(name="lastname") var lastname: String,
    @ColumnInfo(name="telephone") var telephone: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}