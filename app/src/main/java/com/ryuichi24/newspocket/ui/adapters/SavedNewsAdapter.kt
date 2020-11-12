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
import kotlinx.android.synthetic.main.item_news.view.ivArticleImage
import kotlinx.android.synthetic.main.item_news.view.tvDescription
import kotlinx.android.synthetic.main.item_news.view.tvPublishedAt
import kotlinx.android.synthetic.main.item_news.view.tvSource
import kotlinx.android.synthetic.main.item_news.view.tvTitle
import kotlinx.android.synthetic.main.item_saved_news.view.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class SavedNewsAdapter(): RecyclerView.Adapter<SavedNewsAdapter.SavedNewsViewHolder>() {

    inner class SavedNewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(savedArticle: Article) {
            // TODO: create helper class for formatting date
            val parsedDate = LocalDateTime.parse(savedArticle.publishedAt, DateTimeFormatter.ISO_DATE_TIME)
            val formattedDate = parsedDate.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"))

            itemView.tvTitle.text = savedArticle.title
            itemView.tvDescription.text = savedArticle.description
            itemView.tvSource.text = savedArticle.source.name
            itemView.tvPublishedAt.text = formattedDate
            itemView.ivArticleImage.load(savedArticle.urlToImage)

            itemView.addNoteBtn.setOnClickListener {
                addNoteBtnClickListener?.let { listener ->
                    listener()
                }

            }

            itemView.readNoteBtn.setOnClickListener {
                readNoteBtnClickListener?.let { listener ->
                    listener()
                }
            }

            itemView.clArticle.setOnClickListener {
                itemClickListener?.let { listener ->
                    listener(savedArticle)
                }
            }
        }
    }

    // the function will be injected in the fragment
    private var itemClickListener: ((Article) -> Unit)? = null
    private var addNoteBtnClickListener: (() -> Unit)? = null
    private var readNoteBtnClickListener: (() -> Unit)? = null


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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedNewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_saved_news, parent, false)
        return SavedNewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: SavedNewsViewHolder, position: Int) {
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

    fun setAddNoteBtnClickListener(listener: () -> Unit) {
        addNoteBtnClickListener = listener
    }

    fun setReadNoteBtnItemClickListener(listener: () -> Unit) {
        readNoteBtnClickListener = listener
    }
}