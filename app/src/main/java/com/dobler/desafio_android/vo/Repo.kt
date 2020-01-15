package com.dobler.desafio_android.vo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Repo(
    @PrimaryKey val id: Int,
    val name: String,
    val owner: User,
    val full_name: String,
    val description: String,
    val stargazers_count: Int,
    val forks_count: Int
)
