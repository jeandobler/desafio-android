package com.dobler.desafio_android.ui.githubRepository

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dobler.desafio_android.util.paging.Listing
import com.dobler.desafio_android.util.rx.SchedulerContract

class ListRepositoryViewModel(
    val repository: com.dobler.desafio_android.data.repository.githubRepository.GithubRepository,
    private val schedulers: SchedulerContract
) : ViewModel() {

    private var started: Boolean = false
    private var repoResult = MutableLiveData<Listing<com.dobler.desafio_android.vo.GithubRepository>>()

    val githubRepositories = Transformations.switchMap(repoResult, { it.pagedList })!!
    val networkState = Transformations.switchMap(repoResult, { it.networkState })!!
    val refreshState = Transformations.switchMap(repoResult, { it.refreshState })!!

    fun refresh() {
        repoResult.value?.refresh?.invoke()
    }

    fun retry() {
        val listing = repoResult?.value
        listing?.retry?.invoke()
    }


    fun loadList() {

        if (!started) {
            repository.getPage()
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.ui())
                .subscribe({
                    repoResult.postValue(it)
                    started = true

                }, {
                })

        }
    }


}
