package com.ozanergun.hw2.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Contact::class], version = 4)
abstract class ContactDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao
}