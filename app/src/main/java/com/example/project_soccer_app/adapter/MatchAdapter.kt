package com.example.project_soccer_app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.project_soccer_app.data.database.match.MatchEntity
import com.example.project_soccer_app.databinding.ItemMatchBinding

class MatchAdapter(val context: Context) :
    PagingDataAdapter<MatchEntity, MatchAdapter.MatchItemViewHolder>(MatchItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchItemViewHolder {
        var bind = ItemMatchBinding.inflate(LayoutInflater.from(context), parent, false)
        return MatchItemViewHolder(context, bind)
    }

    override fun onBindViewHolder(holder: MatchItemViewHolder, position: Int) {
        getItem(position)?.let { matchItem ->
            holder.setItem(matchItem)
        }
    }

    class MatchItemViewHolder(val context: Context, var bind: ItemMatchBinding) :
        RecyclerView.ViewHolder(bind.root) {
        fun setItem(item: MatchEntity) {
            bind.matchEntity = item
        }
    }

    class MatchItemDiffCallback : DiffUtil.ItemCallback<MatchEntity>() {
        override fun areItemsTheSame(
            oldItem: MatchEntity,
            newItem: MatchEntity
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MatchEntity,
            newItem: MatchEntity
        ): Boolean {
            return oldItem == newItem
        }

    }
}