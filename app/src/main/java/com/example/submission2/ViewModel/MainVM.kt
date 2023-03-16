package com.example.submission2.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission2.Api.GithubClient
import com.example.submission2.DataBase.User
import com.example.submission2.DataBase.responseUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainVM: ViewModel() {
    val listUser = MutableLiveData<ArrayList<User>>()

    fun getSearchUser(): LiveData<ArrayList<User>>{
        return listUser
    }

    fun setSearchUser(query: String){
        GithubClient.apiInstance
            .getSearchUser(query)
            .enqueue(object : Callback<responseUser>{
                override fun onResponse(
                    call: Call<responseUser>,
                    response: Response<responseUser>
                ) {
                    if(response.isSuccessful){
                        listUser.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<responseUser>, t: Throwable) {
                }
            })
    }


}