package com.dobler.desafio_android.data.repository.githubRepository

import com.dobler.desafio_android.data.api.GithubRepositoryResponse
import com.dobler.desafio_android.data.api.GithubService

class GithubRepository(private val api: GithubService) {


    suspend fun getPage(): GithubRepositoryResponse = api.getRepositoriesPage("language:Java", "stars", 1)



//                    return api.getPullsList()

//        val livePagedList = sourceFactory.toLiveData(
//            pageSize = 30,
//            fetchExecutor = networkExecutor
//        )
//
//        val refreshState = Transformations.switchMap(sourceFactory.sourceLiveData) {
//            it.initialLoad
//        }
//        return Listing(
//                pagedList = livePagedList,
//                networkState = Transformations.switchMap(sourceFactory.sourceLiveData) {
//                    it.networkState
//                },
//                retry = {
//                    sourceFactory.sourceLiveData.value?.retryAllFailed()
//                },
//                refresh = {
//                    sourceFactory.sourceLiveData.value?.invalidate()
//                },
//                refreshState = refreshState
//        )
//    }
}