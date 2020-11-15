package com.ryuichi24.newspocket.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ryuichi24.newspocket.R
import com.ryuichi24.newspocket.models.Article
import com.ryuichi24.newspocket.models.ArticleWithTag
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

        fun bind(savedArticleWithTag: ArticleWithTag) {
            // TODO: create helper class for formatting date
            val savedArticle = savedArticleWithTag.article
            val tag = savedArticleWithTag.tag

            val parsedDate = LocalDateTime.parse(savedArticle.publishedAt, DateTimeFormatter.ISO_DATE_TIME)
            val formattedDate = parsedDate.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"))

            itemView.tvTitle.text = savedArticle.title
            itemView.tvDescription.text = savedArticle.description
            itemView.tvSource.text = savedArticle.source.name
            itemView.tvPublishedAt.text = formattedDate
            itemView.ivArticleImage.load(savedArticle.urlToImage)

            itemView.tvTag.text = tag.name

            itemView.btnTagSetting.setOnClickListener {
                btnTagSettingClickListener?.let { listener ->
                    listener(savedArticle, itemView.btnTagSetting)
                }
            }

            itemView.btnGoAddNote.setOnClickListener {
                btnAddNoteClickListener?.let { listener ->
                    listener(savedArticle)
                }

            }

            itemView.btnGoNoteList.setOnClickListener {
                btnReadNoteClickListener?.let { listener ->
                    listener(savedArticle)
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
    private var btnTagSettingClickListener: ((Article, ImageButton) -> Unit)? = null
    private var itemClickListener: ((Article) -> Unit)? = null
    private var btnAddNoteClickListener: (((Article)) -> Unit)? = null
    private var btnReadNoteClickListener: (((Article)) -> Unit)? = null


    // setup DiffUtil
    private val differCallback = object : DiffUtil.ItemCallback<ArticleWithTag>() {
        override fun areItemsTheSame(oldItem: ArticleWithTag, newItem: ArticleWithTag): Boolean {
            return oldItem.article.url == newItem.article.url
        }

        override fun areContentsTheSame(oldItem: ArticleWithTag, newItem: ArticleWithTag): Boolean {
            return oldItem == newItem
        }
    }

    // differ list
    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedNewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_saved_news, parent, false)
        return SavedNewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: SavedNewsViewHolder, position: Int) {
        val articleWithTag = differ.currentList[position]
        holder.bind(articleWithTag)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    // will be used in the fragment
    fun setBtnTagSettingClickListener(listener: (Article, ImageButton) -> Unit) {
        btnTagSettingClickListener = listener
    }

    fun setItemClickListener(listener: (Article) -> Unit) {
        itemClickListener = listener
    }

    fun setBtnAddNoteClickListener(listener: (Article) -> Unit) {
        btnAddNoteClickListener = listener
    }

    fun setBtnReadNoteItemClickListener(listener: (Article) -> Unit) {
        btnReadNoteClickListener = listener
    }
}