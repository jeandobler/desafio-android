package com.dobler.desafio_android.ui.pull.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.CircleCropTransformation
import com.dobler.desafio_android.databinding.ListRvPullRequestBinding
import com.dobler.desafio_android.vo.PullRequest
import kotlinx.android.synthetic.main.list_rv_user.view.*

class PullRequestListAdapter() :
    RecyclerView.Adapter<PullRequestListAdapter.PullRequestViewHolder>() {

    var dataset: ArrayList<PullRequest> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PullRequestListAdapter.PullRequestViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding: ListRvPullRequestBinding = ListRvPullRequestBinding.inflate(
            inflater,
            parent,
            false
        )

        return PullRequestViewHolder(binding)
    }

    fun addPullRequestsList(data: List<PullRequest>) {
        dataset.addAll(data)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: PullRequestViewHolder, position: Int) {

        holder.bind(dataset[position])

        holder.itemView.ivUserImage.load(dataset[position].user.avatar_url) {
            crossfade(true)
            transformations(CircleCropTransformation())
        }
    }

    override fun getItemCount() = dataset.size

    class PullRequestViewHolder(val view: ListRvPullRequestBinding) : RecyclerView.ViewHolder(view.root) {

        fun bind(pullRequest: PullRequest) {
            view.pulls = pullRequest
            view.executePendingBindings()
        }
    }
}
