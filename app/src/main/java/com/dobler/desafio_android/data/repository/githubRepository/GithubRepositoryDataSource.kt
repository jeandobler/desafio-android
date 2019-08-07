package com.dobler.desafio_android.data.repository.githubRepository


import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.dobler.desafio_android.data.api.GithubRepositoryService
import com.dobler.desafio_android.vo.GithubRepository

class GithubRepositoryDataSource(
    private val api: GithubRepositoryService
) : DataSource.Factory<String, GithubRepository>() {

    val sourceLiveData = MutableLiveData<PageKeyedSubredditDataSource>()
    override fun create(): DataSource<String, GithubRepository> {
        val source = PageKeyedSubredditDataSource(api)
        sourceLiveData.postValue(source)
        return source
    }
}