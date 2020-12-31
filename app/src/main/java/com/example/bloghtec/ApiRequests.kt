package com.example.bloghtec

import com.example.bloghtec.API.PostItem
import retrofit2.Call
import retrofit2.http.GET

interface ApiRequests {

    @GET("posts")
    fun getPosts(): Call<List<PostItem>>
}