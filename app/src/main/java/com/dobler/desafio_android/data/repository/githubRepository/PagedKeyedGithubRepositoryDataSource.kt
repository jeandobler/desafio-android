package com.dobler.desafio_android.data.repository.githubRepository

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.dobler.desafio_android.data.api.GithubService
import com.dobler.desafio_android.util.paging.NetworkState
import com.dobler.desafio_android.vo.Repo
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.IOException
import java.util.concurrent.Executors

class PageKeyedSubredditDataSource(
    private val api: GithubService
) : PageKeyedDataSource<String, Repo>() {

    private val retryExecutor = Executors.newFixedThreadPool(5)

    private var retry: (() -> Any)? = null

    var page = 1

    val networkState = MutableLiveData<NetworkState>()

    val initialLoad = MutableLiveData<NetworkState>()

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.let {
            retryExecutor.execute {
                it.invoke()
            }
        }
    }

    override fun loadBefore(
        params: LoadParams<String>,
        callback: LoadCallback<String, Repo>
    ) {
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, Repo>) {



        runBlocking {
            launch {
                try {
                    networkState.postValue(NetworkState.LOADING)
                    val response = api.getPage("language:Java", "stars", ++page)

                    val data = response.items
                    val items = data.map { it }
                    callback.onResult(items, page.toString())
                    networkState.postValue(NetworkState.LOADED)

                } catch (e: Exception) {
                    networkState.postValue(NetworkState.error(e.message ?: "unknown err"))
                }

            }
        }
    }

    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, Repo>
    ) {

        runBlocking {
            launch {

                try {
                    val currentPage = page
                    val response = api.getPage("language:Java", "stars", currentPage)
                    networkState.postValue(NetworkState.LOADING)
                    initialLoad.postValue(NetworkState.LOADING)

                    val data = response.items
                    val items = data.map { it }
                    retry = null
                    networkState.postValue(NetworkState.LOADED)
                    initialLoad.postValue(NetworkState.LOADED)
                    callback.onResult(items, page--.toString(), page++.toString())
                } catch (ioException: IOException) {
                    retry = {
                        loadInitial(params, callback)
                    }
                    val error = NetworkState.error(ioException.message ?: "unknown error")
                    networkState.postValue(error)
                    initialLoad.postValue(error)
                }

            }
        }
    }
}