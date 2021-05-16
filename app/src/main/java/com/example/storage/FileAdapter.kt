package com.example.storage


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import kotlinx.android.synthetic.main.file_item.view.*

class FileAdapter(private val fileList: List<File>, private val listener: OnItemClickListener) : RecyclerView.Adapter<FileAdapter.FileViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.file_item, parent, false)

        return FileViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        val currentItem = fileList[position]
        holder.textView1.text = currentItem.name
    }

    override fun getItemCount() = fileList.size

    inner class FileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val textView1: TextView = itemView.text_view_1

        init {
            itemView.setOnClickListener(this)
            itemView.delete.setOnClickListener {
                val position = adapterPosition
                if(position != RecyclerView.NO_POSITION){
                    listener.onItemDelete(position)
                }
            }
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION){
                listener.onItemClick(position)
            }
        }
    }

    interface  OnItemClickListener {
        fun onItemClick(position: Int)
        fun onItemDelete(position: Int)
    }
}