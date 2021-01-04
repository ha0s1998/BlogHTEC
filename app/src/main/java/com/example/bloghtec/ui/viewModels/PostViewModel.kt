package com.example.bloghtec.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bloghtec.api.PostItem
import com.example.bloghtec.repository.localRepository.LocalRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostViewModel : ViewModel() {
    var localRepositrory : LocalRepository
    val data : MutableLiveData<List<PostItem>?> = MutableLiveData(null)

    init {
        localRepositrory = LocalRepository.getDatabase()
    }

    fun deletePost(postId: Int){
        CoroutineScope(Dispatchers.IO).launch {
            localRepositrory.deletePost(postId)
        }
    }
}