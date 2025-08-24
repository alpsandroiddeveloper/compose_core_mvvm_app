package com.alps.compose_mvvm_strcucture.db

import androidx.room.Database
import androidx.room.RoomDatabase

// This is the Room Database class that holds the database version and entities
@Database(version = 1, entities = [DbProduct::class], exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    // This is the abstract method that will be used to access the DAO (Data Access Object) for products
    abstract fun getFavoriteProducts(): ProductDAO
}