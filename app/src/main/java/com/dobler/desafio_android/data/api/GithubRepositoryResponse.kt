package com.dobler.desafio_android.data.api

import com.dobler.desafio_android.vo.GithubRepository


data class GithubRepositoryResponse(
    val incomplete_results: Boolean,
    val items: List<GithubRepository>,
    val total_count: Int
)