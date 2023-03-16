package com.awais.raza.car.wmr.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.awais.raza.car.wmr.model.ChatData


@Database(
    entities = [ChatData::class], version = 2, exportSchema = false)

abstract class ChatDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}

