package com.example.submission2.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission2.Api.GithubClient
import com.example.submission2.DataBase.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingVM: ViewModel() {
    val listFollowing = MutableLiveData<ArrayList<User>>()

    fun  getListFollowing():LiveData<ArrayList<User>>{
        return listFollowing
    }
    fun setlisfFollowing(username: String){
        GithubClient.apiInstance
            .getFollowing(username)
            .enqueue(object : Callback<ArrayList<User>>{
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    if (response.isSuccessful){
                        listFollowing.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                }

            })
    }

}