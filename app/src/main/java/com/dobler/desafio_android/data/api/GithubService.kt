package com.dobler.desafio_android.data.api

import com.dobler.desafio_android.vo.PullRequest
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {

    @GET("search/repositories")
    suspend fun getRepositoriesPage(
        @Query("q") query: String,
        @Query("sort") sort: String,
        @Query("page") page: Int
    ): GithubRepositoryResponse

    @GET("repos/{user}/{repositoryName}/pulls")
    suspend fun getPullsList(
        @Path("user") user: String,
        @Path("repositoryName") repositoryName: String
    ): List<PullRequest>
}
