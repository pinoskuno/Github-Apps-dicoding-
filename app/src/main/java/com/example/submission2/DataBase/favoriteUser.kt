package com.example.submission2.DataBase

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "favorite_user")
data class favoriteUser(
    val login: String,
    @PrimaryKey
    val id:Int,
    val avatar_url : String
):Serializable
