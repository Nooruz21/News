package com.example.notes.ui.home

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.note.databinding.ItemNewsBinding
import com.example.notes.model.News
import java.text.SimpleDateFormat
import java.util.*

class NewsAdapter :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    private val list = arrayListOf<News>()
    var onClick: ((News) -> Unit)? = null
    var onLongClick: ((Int) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.ViewHolder {
        return ViewHolder(
            ItemNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsAdapter.ViewHolder, position: Int) {
        holder.bind(list[position])

        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(Color.GRAY)
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE)

        }


    }


    override fun getItemCount() = list.size
    fun addItem(news: News?) {
        news?.let {
            list.add(0, it)
            notifyItemInserted(list.indexOf(news))

        }

    }

    fun getItem(it: Int): News {
        return list[it]

    }
    fun getTodayDate(): String {
        return SimpleDateFormat("h:mm, dd MMM yyyy", Locale.getDefault()).format(Date())
    }

    fun deleteItemsAndNotifyAdapter(pos: Int) {
        list.removeAt(pos)
        notifyItemRemoved(pos)
    }

    fun addItems(list: List<News>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }


    inner class ViewHolder(private var binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(news: News) {
            binding.textTittle.text = news.title
            itemView.setOnClickListener {
                onClick?.invoke(news)
            }
            binding.textView.text = getTodayDate()
            itemView.setOnLongClickListener {
                onLongClick?.invoke(adapterPosition)
                return@setOnLongClickListener true
            }



        }
    }


}
