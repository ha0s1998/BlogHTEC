package com.example.bloghtec.ui.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bloghtec.api.PostItem
import com.example.bloghtec.repository.localRepository.LocalRepository
import com.example.bloghtec.repository.remoteRepository.PostService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

public class AllPostsViewModel : ViewModel() {

    var localRepositrory : LocalRepository
    val data : MutableLiveData<List<PostItem>?> = MutableLiveData(null)

    init {
        localRepositrory = LocalRepository.getDatabase()
        getPosts()
    }

    fun getPosts(){
        CoroutineScope(IO).launch {
            while(true){
                loadData()
                delay(10*1000)
            }
        }
    }

    private suspend fun loadData() {
        localRepositrory.getPostsFromCache().also { cachedPosts ->
            if (cachedPosts.isNullOrEmpty()) {
                getPostsFromAPI()
            } else {
                data.postValue(cachedPosts)
            }

        }
    }

    fun getPostsFromAPI() {

        val retrofitData = PostService.getPosts()

        retrofitData.enqueue(object : Callback<List<PostItem>?> {
            override fun onFailure(call: Call<List<PostItem>?>, t: Throwable) {
                Log.d("AllPostsPage", "onFailure" + t.message)
            }

            override fun onResponse(
                call: Call<List<PostItem>?>,
                response: Response<List<PostItem>?>
            ) {
                deleteCachedPosts()
                val apiList = response.body()?: listOf()
                data.postValue(apiList)
                insertPostsToCache(apiList!!)
            }
        })
    }

    private fun insertPostsToCache(list: List<PostItem>) {
        CoroutineScope(IO).launch {
            localRepositrory.insertPostsToCache(list)
        }
    }

    fun deleteCachedPosts() : Job {
        return CoroutineScope(IO).launch {
            localRepositrory.deleteAllPosts()
        }
    }

    fun loadCachedPosts() {
        CoroutineScope(IO).launch {
            data.postValue(localRepositrory.getPostsFromCache())
        }
    }


}