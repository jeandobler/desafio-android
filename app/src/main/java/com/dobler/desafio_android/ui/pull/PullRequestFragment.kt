package com.dobler.desafio_android.ui.pull

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dobler.desafio_android.R
import com.dobler.desafio_android.ui.pull.adapter.PullRequestListAdapter
import kotlinx.android.synthetic.main.fragment_pull_request_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class PullRequestFragment : Fragment() {

    private val viewModel: PullRequestViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            val safeArgs = PullRequestFragmentArgs.fromBundle(it)
            viewModel.user = safeArgs.user
            viewModel.repositoryName = safeArgs.repositoryName
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pull_request_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycleView()
        viewModel.loadList()
    }


    fun setUpRecycleView() {

        val adapter = PullRequestListAdapter()

        val resId = R.anim.layout_rv_animation

        val controller = AnimationUtils.loadLayoutAnimation(context, resId)

        rvPullRequestList.apply {
            this.adapter = adapter
            this.layoutManager = LinearLayoutManager(context)
            this.layoutAnimation = controller
        }

        viewModel.pullRequest.observe(this, androidx.lifecycle.Observer {
            adapter.addPullRequestsList(it)
            rvPullRequestList.adapter?.notifyDataSetChanged();
            rvPullRequestList.scheduleLayoutAnimation()

            if (it != null) {
                pbPullRequest.visibility = View.GONE
            }

            if(it.size ==0){
                tvNoPullRequest.visibility = View.VISIBLE
            }
        })
    }

}

