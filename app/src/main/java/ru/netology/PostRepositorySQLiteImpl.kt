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
        TODO("Not yet implemented")
    }

    override fun save(post: Post) {
       val id = post.id
        val saved = dao.save(post)
        posts = if (id == 0){
            listOf(saved) + posts
        }else{
            posts.map{
                if (it.id !=id) it else saved
            }
        }
        data.value = posts
    }

    override fun share(id: Int) {
        TODO("Not yet implemented")
    }

    override fun removeById(id: Int) {
        TODO("Not yet implemented")
    }

    override fun video(id: Int) {
        TODO("Not yet implemented")
    }

    override fun singlePost(id: Int) {
        TODO("Not yet implemented")
    }
}