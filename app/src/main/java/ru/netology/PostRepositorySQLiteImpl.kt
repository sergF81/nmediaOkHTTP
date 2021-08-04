package ru.netology

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class PostRepositorySQLiteImpl ( private val dao: PostDAO): PostRepository{
private var posts = emptyList<Post>()
    private val data = MutableLiveData(posts)

    init {
        posts = dao.getAll()
        data.value = posts
    }

    override fun getAll(): LiveData<List<Post>> = data
    override fun like(id: Int) {
        dao.like(id)
        posts = posts.map {
            if (it.id != id) it else it.copy(likedByMe = !it.likedByMe)
        }
        posts = posts.map { it ->
            when {
                it.id != id -> it
                it.likedByMe -> {
                    it.copy(likesCount = it.likesCount + 1)
                        .also {
                            transferToK(it.likesCount)
                        }
                }
                else -> it.copy(likesCount = it.likesCount - 1)
                    .also {
                        transferToK(it.likesCount)
                    }
            }
        }
        data.value = posts
    }

    override fun share(id: Int) {
        dao.share(id)
        posts = posts.map {
            if (it.id != id) it else {
                it.copy(sharedCount = it.sharedCount + 1)
                    .also {
                        transferToK(it.sharedCount)
                        println(transferToK(it.sharedCount))
                    }
            }
        }
        data.value = posts
    }

    override fun save(post: Post) {
        val id = post.id
        val saved = dao.save(post)
        posts = if (id == 0) {
            listOf(saved) + posts
        } else {
            posts.map {
                if (it.id != id) it else saved
            }
        }
        data.value = posts
    }



    override fun removeById(id: Int) {
        dao.removeById(id)
        posts = posts.filter { it.id != id }
        data.value = posts
    }

    override fun video(id: Int) {
        TODO("Not yet implemented")
    }

    override fun singlePost(id: Int) {
    }
}