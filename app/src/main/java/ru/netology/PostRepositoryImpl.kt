package ru.netology

import android.app.Application
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.RuntimeException
import java.util.concurrent.TimeUnit

//описание класса для покдлючения к сети
class PostRepositoryImpl(application: Application) : PostRepository {
    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .build()
    private val gson = Gson()
    private val typeToken = object : TypeToken<List<Post>>() {}

    companion object {
        private const val BASE_URL = "http://10.0.2.2:9999"
        private val jsonType = "application/json".toMediaType()

    }
    //--- конец описания класса для покдлючения к сети

    override fun getAll(): LiveData<List<Post>> {
        val request: Request = Request.Builder()
            .url("${BASE_URL}/api/slow.post")
            .build()

        return client.newCall(request)
            .execute()
            .let { it.body?.string()?: throw RuntimeException("Body is null")}
            .let{
                gson.fromJson(it, typeToken.type)
            }
    }

    override fun like(id: Int) {
        TODO("Not yet implemented")
    }

    override fun save(post: Post) {
        TODO("Not yet implemented")
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