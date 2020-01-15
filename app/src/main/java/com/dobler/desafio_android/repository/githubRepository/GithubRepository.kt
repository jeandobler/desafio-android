package com.dobler.desafio_android.repository.githubRepository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dobler.desafio_android.data.api.GithubService
import com.dobler.desafio_android.data.dao.AppDatabase
import com.dobler.desafio_android.vo.Repo
import kotlinx.coroutines.runBlocking

class GithubRepository(val api: GithubService, val appDatabase: AppDatabase) {

    private var isRequestInProgress: Boolean = false
    private var lastRequestedPage: Int = 1


    suspend fun getPage(search: String): LiveData<PagedList<Repo>> {

        val paged = appDatabase.repoDao().getPage()
        // Get the paged list
        val data = LivePagedListBuilder(paged, 20).build()

        requestAndSaveData()
        return data

    }


    private fun requestAndSaveData() {
        if (isRequestInProgress) return

        isRequestInProgress = true

        runBlocking {
            val response = api.getRepositoriesPage("", "language:Java", lastRequestedPage)



            appDatabase.repoDao().insertAll(response.items)
            lastRequestedPage++
            isRequestInProgress = false

//        }, { error ->
//            networkErrors.postValue(error)
//            isRequestInProgress = false
//        })
        }
    }

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
