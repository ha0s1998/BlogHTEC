package com.example.bloghtec.repository.localRepository.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bloghtec.api.PostItem

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<PostItem>)

    @Query("DELETE FROM post_item")
    suspend fun deleteAll()

    @Query("DELETE FROM post_item WHERE id = :id")
    suspend fun deletePost(id: Int)

    @Query("SELECT * FROM post_item")
    suspend fun getAllPosts(): List<PostItem>?
}