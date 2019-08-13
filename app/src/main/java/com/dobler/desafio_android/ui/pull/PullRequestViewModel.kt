package com.dobler.desafio_android.ui.pull

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dobler.desafio_android.data.repository.pullRequest.PullRequestRepository
import com.dobler.desafio_android.util.rx.SchedulerContract
import com.dobler.desafio_android.vo.PullRequest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class PullRequestViewModel(
    private val repository: PullRequestRepository,
    private val schedulers: SchedulerContract
) : ViewModel() {

    val pullRequest = MutableLiveData<List<PullRequest>>()

    var looaded: Boolean = false
    lateinit var user: String
    lateinit var repositoryName: String

    fun loadList() {

        if (!looaded) {

            runBlocking {
                launch {
                    try {
                        repository.getAll(user, repositoryName).let {
                            pullRequest.postValue(it)
                        }

                    } catch (e: Exception) {
                        looaded = false
                    }
                }
            }
        }
    }
}
