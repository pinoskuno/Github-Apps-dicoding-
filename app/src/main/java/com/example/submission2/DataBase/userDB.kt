package com.example.submission2.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [favoriteUser::class],
    version = 1
)
abstract class userDB: RoomDatabase() {
    companion object {
        var INSTANCE: userDB? = null

        fun getDataBase(context: Context): userDB? {
            if (INSTANCE == null) {
                synchronized(userDB::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        userDB::class.java,
                        "dataBaseUser"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
    abstract fun favoriteUserDao():favoriteUserDao
}