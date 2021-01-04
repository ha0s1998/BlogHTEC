package com.example.bloghtec.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import com.example.bloghtec.api.UserDetails
import com.example.bloghtec.R
import com.example.bloghtec.ui.viewModels.PostViewModel
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_post.*
import okhttp3.*
import java.io.IOException

class PostActivity : AppCompatActivity() {

    lateinit var title  :TextView
    lateinit var body :TextView
    lateinit var name :TextView
    lateinit var email :TextView
    var postId :Int? = null

    val viewModel: PostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        var postTitle = intent.getStringExtra("postTitle")
        var postBody = intent.getStringExtra("postBody")
        postId = intent.getIntExtra("postId",-1)

        title = findViewById(R.id.PostTitle)
        body = findViewById(R.id.PostBody)
        name = findViewById(R.id.PostName)
        email = findViewById(R.id.PostEmail)

        title.text = postTitle
        body.text = postBody

        fetchPost()
        initOnClickListeners()
    }

    private fun initOnClickListeners() {
        deleteButton.setOnClickListener {
            if(postId != null) {
                viewModel.deletePost(postId!!)
                Toast.makeText(this, "Deleted $postId. post!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, AllPostsActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun fetchPost() {
        var userId = intent.getIntExtra("userId", -1)
        val postDetailURL = "https://jsonplaceholder.typicode.com/users/" + userId

        val client = OkHttpClient()
        val request = Request.Builder().url(postDetailURL).build()


        client.newCall(request).enqueue(object: Callback{
            override fun onResponse(call: Call, response: Response) {
                val bod = response?.body?.string()
                val gson = GsonBuilder().create()
                val userDetails = gson.fromJson(bod, UserDetails::class.java)

                name.text = userDetails.name
                email.text = userDetails.email
            }

            override fun onFailure(call: Call, e: IOException) {
                Toast.makeText(this@PostActivity, "Failed fetching post details", Toast.LENGTH_SHORT).show()
            }
        })
    }
}