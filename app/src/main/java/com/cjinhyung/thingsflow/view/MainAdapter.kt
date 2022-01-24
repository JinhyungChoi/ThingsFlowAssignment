package com.cjinhyung.thingsflow.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.cjinhyung.thingsflow.databinding.ItemGitIssueBinding
import com.cjinhyung.thingsflow.databinding.ItemThingsflowLogoBinding
import com.cjinhyung.thingsflow.network.GitResponseItem

class MainAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var issues = mutableListOf<GitResponseItem>()

    private var itemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == 0){
            LogoViewHolder(ItemThingsflowLogoBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ))
        } else {
            GitIssueViewHolder(ItemGitIssueBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ))
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position!= 5){
            (holder as GitIssueViewHolder).bind(issues[position])
        } else {
            (holder as LogoViewHolder).bind()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 5){
            0
        } else {
            1
        }
    }

    override fun getItemCount(): Int {
        return issues.size
    }

    inner class GitIssueViewHolder(private val binding: ItemGitIssueBinding) : RecyclerView.ViewHolder(binding.root){
        init{
            itemView.setOnClickListener {
                itemClickListener?.onItemClick(itemView, adapterPosition)
            }
        }

        fun bind(item: GitResponseItem){
            binding.tvIssue.text = "#${item.number}: ${item.title}"
        }
    }

    inner class LogoViewHolder(private val binding: ItemThingsflowLogoBinding) : RecyclerView.ViewHolder(binding.root){
        init{
            itemView.setOnClickListener {
                itemClickListener?.onItemClick(itemView, adapterPosition)
            }
        }

        fun bind(){
            Glide.with(itemView.context)
                .load("https://s3.ap-northeast-2.amazonaws.com/hellobot-kr-test/image/main_logo.png")
                .into(binding.ivThingsflow)
        }
    }
    interface OnItemClickListener{
        fun onItemClick(view: View, position: Int)
    }
    fun setOnItemClickListener(listener: OnItemClickListener){
        itemClickListener = listener
    }
}