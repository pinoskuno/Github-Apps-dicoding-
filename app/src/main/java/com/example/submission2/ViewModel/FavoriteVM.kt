package com.example.submission2.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.submission2.DataBase.favoriteUser
import com.example.submission2.DataBase.favoriteUserDao
import com.example.submission2.DataBase.userDB

class FavoriteVM(application: Application): AndroidViewModel(application)  {
    private val userDao: favoriteUserDao?
    private val userDatabase: userDB?

    init {
        userDatabase= userDB.getDataBase(application)
        userDao= userDatabase?.favoriteUserDao()
    }

    fun getFavoriteUser(): LiveData<List<favoriteUser>>?{
        return userDao?.getFavoriteUser()
    }
}