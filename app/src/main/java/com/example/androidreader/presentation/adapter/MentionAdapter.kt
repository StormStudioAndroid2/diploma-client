package com.example.androidreader.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidreader.databinding.RecyclerViewItemBinding

class MentionAdapter(
    private var dataSet: List<String>
) :
    RecyclerView.Adapter<MentionAdapter.MentionViewHolder>() {

    inner class MentionViewHolder(private val mentionItemBinding: RecyclerViewItemBinding) :
        RecyclerView.ViewHolder(mentionItemBinding.root) {
        val titleTextView: TextView = mentionItemBinding.titleText
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MentionViewHolder {
        val itemBinding =
            RecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MentionViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MentionViewHolder, position: Int) {
        holder.titleTextView.text = dataSet[position]
    }

    override fun getItemCount() = dataSet.size

    fun setData(newDataSet: List<String>) {
        dataSet = newDataSet
        notifyDataSetChanged()
    }
}