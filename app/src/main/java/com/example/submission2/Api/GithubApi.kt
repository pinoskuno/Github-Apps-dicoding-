package com.example.submission2.Api

import com.example.submission2.DataBase.User
import com.example.submission2.DataBase.responseUser
import com.example.submission2.DataBase.DetailUser
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {

    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUser(
        @Query("q") query: String
    ): Call<responseUser>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetailUser(
        @Path("username") username:String
    ): Call<DetailUser>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username:String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username:String
    ): Call<ArrayList<User>>

}