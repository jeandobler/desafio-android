package com.dobler.desafio_android.data.repository.githubRepository


import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.dobler.desafio_android.data.api.GithubService
import com.dobler.desafio_android.vo.Repo

class GithubRepositoryDataSource(
    private val api: GithubService
) : DataSource.Factory<String, Repo>() {

    val sourceLiveData = MutableLiveData<PageKeyedSubredditDataSource>()
    override fun create(): DataSource<String, Repo> {
        val source = PageKeyedSubredditDataSource(api)
        sourceLiveData.postValue(source)
        return source
    }
}