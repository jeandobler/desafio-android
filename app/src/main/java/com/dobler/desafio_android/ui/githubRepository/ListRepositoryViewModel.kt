package com.dobler.desafio_android.ui.githubRepository

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dobler.desafio_android.data.repository.githubRepository.GithubRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListRepositoryViewModel(val repository: GithubRepository) : ViewModel() {

    private val repoResult = MutableLiveData<ListRepositoryState>()
    val githubRepositories = repoResult

    init {
        loadList()
    }

    fun refresh() {
        repoResult.value = Results(emptyList())
        loadList()
    }

    private fun loadList() {

        repoResult.value = ShowLoading

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getPage()
                response.let {
                    if (it.items.isEmpty()) {
                        repoResult.postValue(EmptyResults)
                    } else {
                        repoResult.postValue(Results(it.items))
                    }
                }
            } catch (e: Exception) {
                repoResult.postValue(Error(e.message.toString()))
            }
        }
    }
}
