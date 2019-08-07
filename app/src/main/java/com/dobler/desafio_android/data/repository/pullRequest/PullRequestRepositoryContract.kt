package com.dobler.desafio_android.data.repository.pullRequest

import com.dobler.desafio_android.vo.RepositoryPullRequest
import io.reactivex.Observable

interface PullRequestRepositoryContract {

    fun getAll(user: String, repositoryName: String): Observable<List<RepositoryPullRequest>>
}
