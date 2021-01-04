package com.example.bloghtec.api

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "post_item")
data class PostItem(
    val body: String,
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    var title: String,
    val userId: Int
)