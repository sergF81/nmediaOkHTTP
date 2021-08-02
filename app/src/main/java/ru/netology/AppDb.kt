package ru.netology

import android.content.Context
import android.database.sqlite.SQLiteDatabase

class AppDb private constructor(db: SQLiteDatabase){
    val postDAO: PostDAO = PostDaoImpl(db)

    companion object{
        @Volatile
        private var instance: AppDb? = null

        fun getInstance(context: Context): AppDb{
            return instance ?: synchronized(this){
                instance ?: AppDb(buildDataBase(context, arrayOf(PostDaoImpl.DDL)))
            }
        }

        private fun buildDataBase(context: Context, DDLs: Array<String>) = DbHelper(
            context, 1, "app.db", DDLs,
        ).writableDatabase
    }

}

