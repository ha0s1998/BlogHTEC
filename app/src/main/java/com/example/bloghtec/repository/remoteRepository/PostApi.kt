package com.example.bloghtec.repository.remoteRepository

import com.example.bloghtec.api.PostItem
import retrofit2.Call
import retrofit2.http.GET

interface PostApi {
    @GET("posts")
    fun getPosts(): Call<List<PostItem>>
}