package com.example.kotlinproject1_todolist.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kotlinproject1_todolist.Model.Todo

@Database(entities = [Todo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todoDao() : TodoDao

    companion object{
        private var INSTANCE : AppDatabase? = null
        fun getInstance(context : Context) : AppDatabase {
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "Todo-database"
                ).build()
            }
            return INSTANCE as AppDatabase
        }
    }
}