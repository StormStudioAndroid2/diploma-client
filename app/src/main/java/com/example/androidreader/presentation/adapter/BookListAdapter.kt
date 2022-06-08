package com.example.androidreader.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidreader.databinding.RecyclerViewItemBinding

class BookListAdapter(
    private var dataSet: List<String>,
    private val callback: (index: Int) -> Unit
) :
    RecyclerView.Adapter<BookListAdapter.BookViewHolder>() {

    inner class BookViewHolder(private val mentionItemBinding: RecyclerViewItemBinding) :
        RecyclerView.ViewHolder(mentionItemBinding.root) {
        val titleTextView: TextView = mentionItemBinding.titleText

        init {
            this.mentionItemBinding.root.setOnClickListener {
                if (adapterPosition != -1) {
                    callback.invoke(adapterPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val itemBinding =
            RecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.titleTextView.text = dataSet[position]
    }

    override fun getItemCount() = dataSet.size

    fun setData(newDataSet: List<String>) {
        dataSet = newDataSet
        notifyDataSetChanged()
    }
}