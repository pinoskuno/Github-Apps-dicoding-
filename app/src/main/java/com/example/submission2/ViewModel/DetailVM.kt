package com.example.submission2.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.submission2.Api.GithubClient
import com.example.submission2.DataBase.DetailUser
import com.example.submission2.DataBase.favoriteUser
import com.example.submission2.DataBase.favoriteUserDao
import com.example.submission2.DataBase.userDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailVM(application: Application) : AndroidViewModel(application) {

    val pengguna = MutableLiveData<DetailUser>()

    private val userDao: favoriteUserDao?
    private val userDatabase: userDB?

    init {
        userDatabase= userDB.getDataBase(application)
        userDao= userDatabase?.favoriteUserDao()
    }

    fun getUserDetail(): LiveData<DetailUser> {
        return pengguna
    }

    fun addFavorite(username: String, id:Int, avatarurl: String){
        CoroutineScope(Dispatchers.IO).launch {
            var user = favoriteUser(
                username,
                id,
                avatarurl
            )
            userDao?.addFavorite(user)
        }
    }
    fun checkUser(id: Int)= userDao?.checkUser(id)

    fun removeFavorite(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFavorite(id)
        }
    }

    fun setUserDetail(username: String) {
        GithubClient.apiInstance
            .getDetailUser(username)
            .enqueue(object : Callback<DetailUser> {
                override fun onResponse(
                    call: Call<DetailUser>,
                    response: Response<DetailUser>
                ) {
                    if (response.isSuccessful) {
                        pengguna.postValue(response.body())
                    }
                }
                override fun onFailure(call: Call<DetailUser>, t: Throwable) {
                }
            })
    }
}