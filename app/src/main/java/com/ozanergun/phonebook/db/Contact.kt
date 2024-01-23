package com.ozanergun.hw2.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contactTable")
class Contact (
    @PrimaryKey(autoGenerate = false)
    val phone_no: Int,
    val full_name: String
)