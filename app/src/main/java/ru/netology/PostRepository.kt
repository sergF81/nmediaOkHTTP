package ru.netology

import androidx.lifecycle.LiveData

interface PostRepository {
    fun getAll(): List<Post>
    fun like(id: Int)
    fun save(post: Post)
    fun share(id: Int)
    fun removeById(id: Int)
    fun video(id: Int)
    fun singlePost(id: Int)
}
