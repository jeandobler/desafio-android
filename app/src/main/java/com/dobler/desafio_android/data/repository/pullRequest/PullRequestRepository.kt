package com.dobler.desafio_android.data.repository.pullRequest

import com.dobler.desafio_android.data.api.GithubService
import com.dobler.desafio_android.vo.PullRequest

class PullRequestRepository(private val api: GithubService) {

    suspend fun getAll(user: String, repositoryName: String): List<PullRequest> =
        api.getList(user, repositoryName)

}