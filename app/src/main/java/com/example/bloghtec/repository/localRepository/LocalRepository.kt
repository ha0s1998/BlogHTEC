package com.example.bloghtec.repository.localRepository

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bloghtec.api.PostItem
import com.example.bloghtec.app.MyApplication
import com.example.bloghtec.repository.localRepository.dao.PostDao

@Database(entities = [PostItem::class], version = 5, exportSchema = false)
abstract class LocalRepository :RoomDatabase() {
    abstract fun getPostDao(): PostDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: LocalRepository? = null

        fun getDatabase(): LocalRepository {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE
                ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    MyApplication.context(),
                    LocalRepository::class.java,
                    "HTECBlog.db"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

    suspend fun getPostsFromCache(): List<PostItem>?{
        return getPostDao().getAllPosts()
    }

    suspend fun insertPostsToCache(list: List<PostItem>){
        getPostDao().insertAll(list)
        Observer._isModified.postValue(false)
    }

    suspend fun deleteAllPosts(){
        getPostDao().deleteAll()
    }

    suspend fun deletePost(id: Int) {
        getPostDao().deletePost(id)
        Observer._isModified.postValue(true)
    }
}