package com.dobler.desafio_android.data.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dobler.desafio_android.vo.Repo

@Database(entities = arrayOf(Repo::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun repoDao(): RepoDao
}

