package com.dobler.desafio_android.data.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.dobler.desafio_android.vo.Repo

@Dao
interface RepoDao {

    @Query("SELECT * FROM repo")
    fun getAll(): List<Repo>

    @Query("SELECT * FROM repo")
    fun getPage(): DataSource.Factory<Int, Repo>

    @Query("SELECT * FROM repo WHERE name IN (:repoNames)")
    fun loadAllByName(repoNames: List<String>): List<Repo>

    @Query(
        "SELECT * FROM repo WHERE first_name LIKE :first AND " +
                "last_name LIKE :last LIMIT 1"
    )
    fun findByName(first: String, last: String): Repo

    @Insert
    fun insertAll(repos: List<Repo>)

    @Delete
    fun deleteAll(repo: Repo)


}
