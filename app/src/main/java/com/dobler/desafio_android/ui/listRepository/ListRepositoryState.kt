package com.dobler.desafio_android.ui.listRepository

import com.dobler.desafio_android.vo.Repo

sealed class ListRepositoryState

class Results(val response: List<Repo>) : ListRepositoryState()
class Error(val error: String) : ListRepositoryState()
object EmptyResults : ListRepositoryState()
object ShowLoading : ListRepositoryState()
