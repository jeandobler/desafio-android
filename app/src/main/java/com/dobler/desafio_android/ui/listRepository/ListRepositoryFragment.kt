package com.dobler.desafio_android.ui.listRepository

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dobler.desafio_android.databinding.FragmentRepositoryListBinding
import kotlinx.android.synthetic.main.fragment_repository_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListRepositoryFragment : Fragment() {

    private val viewModel: ListRepositoryViewModel by viewModel()

    private lateinit var binding: FragmentRepositoryListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRepositoryListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycleView()
        initSwipeToRefresh()
    }

    private fun initSwipeToRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    private fun setUpRecycleView() {

        val adapter =
            RepositoryListAdapter {
                val action =
                    ListRepositoryFragmentDirections.actionRepositoryListFragmentToPullRequestFragment(
                        it.owner.login,
                        it.name
                    )
                findNavController().navigate(action)
            }

        rvMainRepositoryList.apply {
            this.adapter = adapter
            this.layoutManager = LinearLayoutManager(context)
        }

        viewModel.githubRepositories.observe(this,
            Observer {
                when (it) {
                    is Results -> {
                        binding.tvNoRepositories.visibility = View.GONE
                        adapter.submitList(it.response)
                        hideLoading()
                    }
                    is EmptyResults -> {
                        binding.tvNoRepositories.visibility = View.VISIBLE
                        hideLoading()
                    }
                    is Error -> {
                        binding.tvNoRepositories.visibility = View.VISIBLE
                        hideLoading()
                        Log.e("Bro", it.error)
                    }
                    is ShowLoading -> {
                        showLoading()
                    }
                }
            })
    }

    fun setRefreshListState(load: Boolean) {
        if (binding.swipeRefresh.isRefreshing != load) {
            binding.swipeRefresh.isRefreshing = load

        }
    }

    fun showLoading() {
        setRefreshListState(true)
    }

    fun hideLoading() {
        setRefreshListState(false)
    }
}
