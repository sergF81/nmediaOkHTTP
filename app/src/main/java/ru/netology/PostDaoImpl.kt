package ru.netology

import android.content.ContentValues
import android.database.AbstractCursor
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.FileObserver.CREATE
import androidx.core.content.contentValuesOf
import androidx.lifecycle.Transformations.map

class PostDaoImpl(private val db: SQLiteDatabase) : PostDAO {
    companion object {
        val DDL: String = "CREATE TABLE posts( id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " author TEXT NOT NULL," +
                " content TEXT NOT NULL," +
                " published TEXT NOT NULL," +
                " likedByMe BOOLEAN NOT NULL DEFAULT false," +
                " lakes INTEGER NOT NULL DEFAULT 0," +
                " share INTEGER NOT NULL DEFAULT 0);"
    }

    object PostColumns {
        const val TABLE = "posts"
        const val COLUMN_ID = "id"
        const val COLUMN_AUTHOR = "author"
        const val COLUMN_CONTENT = "content"
        const val COLUMN_PUBLISHED = "published"
        const val COLUMN_LIKED_BY_ME = "likedByMe"
        const val COLUMN_LIKES = "likesCount"
        const val COLUMN_SHARE = "shareCount"
        val ALL_COLUMNS = arrayOf(
            COLUMN_ID,
            COLUMN_AUTHOR,
            COLUMN_CONTENT,
            COLUMN_PUBLISHED,
            COLUMN_LIKED_BY_ME,
            COLUMN_LIKES,
            COLUMN_SHARE
        )
    }

    override fun getAll(): List<Post> {
        val posts = mutableListOf<Post>()
        db.query(
            PostColumns.TABLE,
            PostColumns.ALL_COLUMNS,
            null,
            null,
            null,
            null,
            "${PostColumns.COLUMN_ID} DESC"
        )
            .use {
                while (it.moveToNext()) {
                    posts.add(map(it))
                }
            }
        return posts
    }

    override fun save(post: Post): Post {
        val values = ContentValues().apply {
            if (post.id != 0) {
                put(PostColumns.COLUMN_ID, post.id)
            }
            put(PostColumns.COLUMN_AUTHOR, "Me")
            put(PostColumns.COLUMN_CONTENT, post.content)
            put(PostColumns.COLUMN_PUBLISHED, "now")
        }
        val id = db.replace(PostColumns.TABLE, null, values)
        db.query(
            PostColumns.TABLE,
            PostColumns.ALL_COLUMNS,
            "${PostColumns.COLUMN_ID} = ?",
            arrayOf(id.toString()),
            null,
            null,
            null,
        ).use {
            it.moveToNext()
            return map(it)
        }
    }

    override fun like(id: Int) {
        db.execSQL(
            """
             UPDATE posts SET
                likes = likes + CASE WHEN likedByMe THEN -1 ELSE 1 END,
                likedByMe = CASE WHEN likedByMe THEN 0 ELSE 1 END
                WHERE id =?;
            """.trimIndent(), arrayOf(id)
        )
    }

    override fun share(id: Int) {
        TODO("Not yet implemented")
    }

    override fun removeById(id: Int) {
        db.delete(
            PostColumns.TABLE,
            "${PostColumns.COLUMN_ID} = ?",
            arrayOf(id.toString())
        )
    }

    override fun singlePost(id: Int) {
        TODO("Not yet implemented")
    }

    override fun video(id: Int) {
        TODO("Not yet implemented")
    }


    private fun map(cursor: Cursor): Post {
        with(cursor) {
            return Post(
                id = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_ID)),
                author = getString(getColumnIndexOrThrow(PostColumns.COLUMN_AUTHOR)),
                content = getString(getColumnIndexOrThrow(PostColumns.COLUMN_CONTENT)),
                published = getString(getColumnIndexOrThrow(PostColumns.COLUMN_PUBLISHED)),
                likedByMe = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_LIKED_BY_ME)) != 0,
                likesCount = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_LIKES)),
                sharedCount = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_SHARE))
            )

        }
    }
}