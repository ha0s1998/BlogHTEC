package com.example.bloghtec.util.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bloghtec.api.PostItem
import com.example.bloghtec.R
import com.example.bloghtec.ui.activities.AllPostsActivity
import kotlinx.android.synthetic.main.card_view.view.*

class Adapter(val list: List<PostItem>,
              val listener: AllPostsActivity
) : RecyclerView.Adapter<Adapter.ViewHolder>() {


    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView),
    View.OnClickListener{
        var title: TextView = itemView.PostView
        var body: TextView = itemView.FirstLineView
        
        

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if(position!= RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_view, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = list[position]

        holder.title.text = post.title
        holder.body.text = post.body
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }
}