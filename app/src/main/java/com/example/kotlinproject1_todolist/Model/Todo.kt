package com.example.kotlinproject1_todolist.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(var title : String , var comment : String) {
    @PrimaryKey(autoGenerate = true) var id : Int = 0
}