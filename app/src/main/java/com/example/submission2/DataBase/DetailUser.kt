package com.example.submission2.DataBase

data class DetailUser(
    val login: String,
    val id: Int,
    val avatar_url:String,
    val followers_url:String,
    val following_url:String,
    val name:String,
    val company:String,
    val email:String,
    val following:Int,
    val followers:Int
)
