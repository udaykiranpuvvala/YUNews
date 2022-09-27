package com.unik.yunews.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.unik.yunews.R
import com.unik.yunews.databinding.SingleTopicsBinding

class TopicsAdapter(val context: Context, val topicsList: ArrayList<String>,val onItemClicked:(ky : String)-> Unit) :
    RecyclerView.Adapter<TopicsAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: SingleTopicsBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: SingleTopicsBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.single_topics,parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.txtTopicTitle.text = topicsList[holder.adapterPosition]
        holder.itemView.setOnClickListener {
            onItemClicked(topicsList[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int {
        return topicsList.size
    }
}