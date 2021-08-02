package ru.netology

import androidx.lifecycle.LiveData

interface PostDAO {

    fun getAll(): List<Post>
    fun save(post: Post):Post
    fun like(id: Int)
    fun share(id: Int)
    fun removeById(id: Int)
    fun singlePost(id: Int)
    fun video(id: Int)
}

//interface PostRepository {
//    fun get(): LiveData<List<Post>>
//    fun like(id: Int)
//    fun save(post: Post)
//    fun share(id: Int)
//    fun removeById(id: Int)
//    fun video(id: Int)
//    fun singlePost(id: Int)
//}