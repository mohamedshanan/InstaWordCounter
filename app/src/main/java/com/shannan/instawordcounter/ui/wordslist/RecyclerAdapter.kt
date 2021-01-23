package com.shannan.instawordcounter.ui.wordslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shannan.instawordcounter.R

class RecyclerAdapter(private val words: Array<Pair<String, Int>>) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.word_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = words.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val wordItem = words[position]
        holder.bindPhoto(wordItem)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val wordTextView: TextView = view.findViewById(R.id.word)
        private val countTextView: TextView = view.findViewById(R.id.count)

        fun bindPhoto(word: Pair<String, Int>) {
            wordTextView.text = word.first
            countTextView.text = word.second.toString()
        }
    }

}