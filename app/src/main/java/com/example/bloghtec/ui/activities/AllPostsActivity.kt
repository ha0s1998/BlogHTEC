package com.example.bloghtec.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bloghtec.api.PostItem
import com.example.bloghtec.util.adapter.Adapter
import com.example.bloghtec.R
import com.example.bloghtec.ui.viewModels.AllPostsViewModel
import kotlinx.android.synthetic.main.activity_all_posts.*


class AllPostsActivity : AppCompatActivity(),
    Adapter.OnItemClickListener {

    val viewModel: AllPostsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_posts)

        with(recyclerView){
            layoutManager = LinearLayoutManager(this@AllPostsActivity)
            setHasFixedSize(true)
        }

        initOnClickListeners()

    }

    private fun initOnClickListeners() {
        floatingActionButton.setOnClickListener {
            viewModel.getPostsFromAPI()
        }
    }

    //Activity -> ViewModel(LiveData)
    override fun onStart() {
        super.onStart()

        viewModel.data.observe(this, Observer { list ->
            if (list != null) {
                displayList(list)
                Toast.makeText(this, "Refreshing...", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun displayList(list: List<PostItem>?) {
        var adapter =
            Adapter(list ?: emptyList(), this)
        adapter.notifyDataSetChanged()
        recyclerView.adapter = adapter
    }

    override fun onItemClick(position: Int) {
        Toast.makeText(this, "Item $position clicked", Toast.LENGTH_SHORT).show()
        println("Item $position clicked")
        //data : MutableLiveData<List<PostItem>>
        var clickedItem = viewModel.data.value!![position]
        var intent = Intent(this, PostActivity::class.java)
        intent.putExtra("postTitle", clickedItem.title)
        intent.putExtra("postBody", clickedItem.body)
        intent.putExtra("userId", clickedItem.userId)
        intent.putExtra("postId", clickedItem.id)
        startActivity(intent)
        //adapter.notifyItemChanged(position)
    }
}


