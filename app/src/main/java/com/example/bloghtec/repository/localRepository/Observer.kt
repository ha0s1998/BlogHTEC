package com.example.bloghtec.repository.localRepository

import androidx.lifecycle.MutableLiveData

object Observer {
    var _isModified = MutableLiveData(false)
    fun isCacheModified() = _isModified.value!!
}