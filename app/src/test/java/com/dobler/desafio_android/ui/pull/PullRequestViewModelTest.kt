package com.dobler.desafio_android.ui.pull

import com.dobler.desafio_android.ui.listRepository.ListRepositoryViewModel
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.inject

class PullRequestViewModelTest : KoinTest {

    val repositoryViewModel by inject<ListRepositoryViewModel>()


    @Test
    fun tesKoinComponents() {
        repositoryViewModel.refresh()

//        repositoryViewModel.githubRepositories.observe( this,  Observer { })

    }


}