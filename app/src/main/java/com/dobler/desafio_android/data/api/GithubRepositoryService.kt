package com.dobler.desafio_android.data.api

import com.dobler.desafio_android.vo.RepositoryPullRequest
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface GithubRepositoryService {

    @GET("search/repositories")
    suspend fun getPage(
        @Query("q") query: String,
        @Query("sort") sort: String,
        @Query("page") page: Int
    ): GithubRepositoryResponse

    @GET("repos/{user}/{repositoryName}/pulls")
    fun getList(
        @Path("user") user: String,
        @Path("repositoryName") repositoryName: String
    ):
            List<RepositoryPullRequest>


}
