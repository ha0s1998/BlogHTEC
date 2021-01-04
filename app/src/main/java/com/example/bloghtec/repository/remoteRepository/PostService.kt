package com.example.bloghtec.repository.remoteRepository

import com.example.bloghtec.api.PostItem
import com.example.bloghtec.util.constants.Constants
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PostService {
    companion object {
        fun getPosts(): Call<List<PostItem>> {
            var api = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PostApi::class.java)

            return api.getPosts()
        }
    }

}