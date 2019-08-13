package com.dobler.desafio_android.ui.githubRepository

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dobler.desafio_android.R
import com.dobler.desafio_android.ui.githubRepository.adapter.RepositoryListAdapter
import com.dobler.desafio_android.vo.Error
import com.dobler.desafio_android.vo.Loading
import com.dobler.desafio_android.vo.Success
import kotlinx.android.synthetic.main.fragment_repository_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListRepositoryFragment : Fragment() {

    private val viewModel: ListRepositoryViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_repository_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycleView()
        initSwipeToRefresh()
        viewModel.loadList()
    }


    private fun initSwipeToRefresh() {
//        viewModel.refreshState.observe(this, Observer {
//            swipe_refresh.isRefreshing = it == NetworkState.LOADING
//        })
        swipe_refresh.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    private fun setUpRecycleView() {

        val adapter = RepositoryListAdapter {
            val action = ListRepositoryFragmentDirections.
                actionRepositoryListFragmentToPullRequestFragment( it.owner.login, it.name)
            findNavController().navigate(action)
        }

        rvMainRepositoryList.apply {
            this.adapter = adapter
            this.layoutManager = LinearLayoutManager(context)
        }

        viewModel.githubRepositories.observe(this,
            Observer {
                when (it) {
                    is Success -> {

                        if (it.response.isEmpty()) {
                            tvNoRepositories.visibility = View.VISIBLE
                        } else {
                            adapter.submitList(it.response)
                        }
                        setRefreshListState(false)
                    }
                    is Error -> {
                        setRefreshListState(false)
                    }
                    is Loading -> {
                        setRefreshListState(true)
                    }
                }
            })
    }

    fun setRefreshListState(load: Boolean) {
        if (swipe_refresh.isRefreshing != load) {
            swipe_refresh.isRefreshing = load
        }
    }


}
