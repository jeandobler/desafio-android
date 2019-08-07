package com.dobler.desafio_android.data.repository.pullRequest

import androidx.annotation.MainThread
import com.dobler.desafio_android.data.api.GithubRepositoryService
import com.dobler.desafio_android.vo.RepositoryPullRequest
import io.reactivex.Observable

class PullRequestRepository(private val api: GithubRepositoryService) :
    PullRequestRepositoryContract {

    @MainThread
    override fun getAll(user: String, repositoryName: String): Observable<List<RepositoryPullRequest>> {
        return api.getList(user, repositoryName)
    }
}