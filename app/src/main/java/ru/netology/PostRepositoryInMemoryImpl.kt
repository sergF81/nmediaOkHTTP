package ru.netology

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class PostRepositoryInMemoryImpl : PostRepository {
    private var nextId = 1

    private var posts = listOf(
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            likedByMe = false,
            likesCount = 99,
            sharedCount = 99
        ),
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Это какая то непонятная жесть!",
            published = "5 июля в 00:30",
            likedByMe = false,
            likesCount = 101,
            sharedCount = 9983,
            video = "https://www.youtube.com/watch?v=WhWc3b3KhnY"
        ),
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Что то косяк какой то вышел!",
            published = "5 июля в 00:30",
            likedByMe = false,
            likesCount = 10,
            sharedCount = 203013
        ),
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Тут нужен очень длинный текст, чтобы увидет отличие постов, а то с короткими неудобно что-либо оценивать!",
            published = "5 июля в 00:30",
            likedByMe = false,
            likesCount = 12403,
            sharedCount = 98
        )
    )

    private val data = MutableLiveData(posts)

    override fun get(): LiveData<List<Post>> = data

    override fun like(id: Int) {
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

    override fun removeById(id: Int) {

        posts = posts.filter { it.id != id }
        data.value = posts
    }

    override fun save(post: Post) {
        if (post.id == 0) {
            posts = listOf(
                post.copy(
                    id = nextId++, author = "Me", likedByMe = false, published = "Now", likesCount = 0, sharedCount = 0
                )
            ) + posts
        } else {
posts = posts.map{if (it.id != post.id) it else it.copy(content = post.content)}
        }
        data.value = posts

    }

    override fun video(id: Int) {
        TODO("Not yet implemented")
    }

}