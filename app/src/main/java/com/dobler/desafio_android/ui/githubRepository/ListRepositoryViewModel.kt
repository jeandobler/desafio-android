package com.dobler.desafio_android.ui.githubRepository

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dobler.desafio_android.data.repository.githubRepository.GithubRepository
import com.dobler.desafio_android.vo.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ListRepositoryViewModel(val repository: GithubRepository) : ViewModel() {

    private var started: Boolean = false

    private val repoResult = MutableLiveData<ResponseState<List<Repo>>>()
    val githubRepositories = Transformations.map(repoResult) { it }

    fun refresh() {
        repoResult.postValue(Success(emptyList()))
        loadList()
    }

    fun loadList() {

        repoResult.postValue(Loading())

        runBlocking {
            launch {
                try {

                    val response = repository.getPage()
                    response.let {
                        repoResult.postValue(Success(it.items))
                        started = true
                    }
                } catch (e: Exception) {
                    started = true
                    repoResult.postValue(Error(e.message.toString()))

                }

            }


        }
    }


}
