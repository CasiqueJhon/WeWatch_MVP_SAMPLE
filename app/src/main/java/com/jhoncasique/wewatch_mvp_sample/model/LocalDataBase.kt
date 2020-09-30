package com.jhoncasique.wewatch_mvp_sample.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Movie::class], version = 1)
abstract class LocalDataBase : RoomDatabase() {
    // --- DAO ---
    abstract fun movieDao(): MovieDao
    companion object {

        // --- SINGLETON ---
        @Volatile
        private var INSTANCE: LocalDataBase? = null

        fun getInstance(context: Context): LocalDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LocalDataBase::class.java,
                    "Word_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}