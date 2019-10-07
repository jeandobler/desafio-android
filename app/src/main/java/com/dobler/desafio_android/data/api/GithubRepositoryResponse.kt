package com.dobler.desafio_android.data.api

import com.dobler.desafio_android.vo.Repo

data class GithubRepositoryResponse(
    val incomplete_results: Boolean,
    val items: List<Repo>,
    val total_count: Int
)
