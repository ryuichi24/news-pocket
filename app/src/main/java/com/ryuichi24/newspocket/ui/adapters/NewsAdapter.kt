package com.ryuichi24.newspocket.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ryuichi24.newspocket.R
import com.ryuichi24.newspocket.models.Article
import kotlinx.android.synthetic.main.item_news.view.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class NewsAdapter(): RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(article: Article) {
            // format published date
            val parsedDate = LocalDateTime.parse(article.publishedAt, DateTimeFormatter.ISO_DATE_TIME)
            val formattedDate = parsedDate.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"))

            itemView.tvTitle.text = article.title
            itemView.tvDescription.text = article.description
            itemView.tvSource.text = article.source.name
            itemView.tvPublishedAt.text = formattedDate
            itemView.ivArticleImage.load(article.urlToImage)

            itemView.setOnClickListener {
                itemClickListener?.let { listener ->
                    listener(article)
                }
            }
        }

    }

    // the function will be injected in the fragment
    private var itemClickListener: ((Article) -> Unit)? = null

    // setup DiffUtil
    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    // differ list
    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.bind(article)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    // will be used in the fragment
    fun setItemClickListener(listener: (Article) -> Unit) {
        itemClickListener = listener
    }
}