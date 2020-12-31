package com.example.bloghtec

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bloghtec.API.PostItem
import kotlinx.android.synthetic.main.activity_all_posts_page.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://jsonplaceholder.typicode.com/"

class AllPostsPage : AppCompatActivity() {

    lateinit var adapter: Adapter
    lateinit var linearLayoutManager: LinearLayoutManager

    //var recyclerView: RecyclerView? = findViewById(R.id.recyclerView)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_posts_page)

        recyclerView.setHasFixedSize(true)

        var layoutMenager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutMenager



        getCurrentData()

        /*var post1: PostItem =  PostItem("TITLE",1, "Texttttt", 2)
        var post2:  PostItem =  PostItem("TITLE2222",2, "Texttttt222", 4)
        postsList.add(post1)
        postsList.add(post2)


        var adapter = Adapter(postsList)
        recyclerView?.adapter = adapter*/
    }

    private fun getCurrentData(){
        var api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)

        val retrofitData = api.getPosts()

        retrofitData.enqueue(object : Callback<List<PostItem>?> {
            override fun onFailure(call: Call<List<PostItem>?>, t: Throwable) {
                d("AllPostsPage", "onFailure" +t.message)
            }

            override fun onResponse(
                call: Call<List<PostItem>?>,
                response: Response<List<PostItem>?>
            ) {
                val responseBody = response.body()!!
                adapter = Adapter(baseContext, responseBody)
                adapter.notifyDataSetChanged()
                recyclerView.adapter = adapter
            }
        })


    }
}


