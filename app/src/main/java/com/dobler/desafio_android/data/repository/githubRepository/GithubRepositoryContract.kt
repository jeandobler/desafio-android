package com.dobler.desafio_android.data.repository.githubRepository

import com.dobler.desafio_android.vo.GithubRepository
import com.dobler.desafio_android.util.paging.Listing
import io.reactivex.Observable

interface GithubRepositoryContract {

    fun getPage(): Observable<Listing<GithubRepository>>

}
