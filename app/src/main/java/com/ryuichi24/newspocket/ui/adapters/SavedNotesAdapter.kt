package com.ryuichi24.newspocket.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ryuichi24.newspocket.R
import com.ryuichi24.newspocket.models.Article
import com.ryuichi24.newspocket.models.Note
import kotlinx.android.synthetic.main.item_note.view.*
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter

class SavedNotesAdapter() : RecyclerView.Adapter<SavedNotesAdapter.SavedNotesViewHolder>() {

    inner class SavedNotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(savedNote: Note) {
            val simpleDateFormat = SimpleDateFormat("MM-dd-yyyy")

            itemView.tvNoteDate.text = simpleDateFormat.format(savedNote.date)
            itemView.tvNoteContent.text = savedNote.text
            itemView.tvNoteContent.setOnClickListener {
                tvNoteContentClickListener?.let { listener ->
                    listener(savedNote)
                }
            }
            itemView.btnDeleteNote.setOnClickListener {
                deleteBtnClickListener?.let { listener ->
                    listener(savedNote)
                }
            }
        }
    }

    // the function will be injected in the fragment
    private var deleteBtnClickListener: ((Note) -> Unit)? = null
    private var tvNoteContentClickListener: ((Note) -> Unit)? = null

    // setup DiffUtil
    private val differCallback = object : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.noteId == newItem.noteId
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }

    // differ list
    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedNotesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_note, parent, false)
        return SavedNotesViewHolder(view)
    }

    override fun onBindViewHolder(holder: SavedNotesViewHolder, position: Int) {
        val note = differ.currentList[position]
        holder.bind(note)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    // will be used in the fragment
    fun setDeleteBtnClickListener(listener: (Note) -> Unit) {
        deleteBtnClickListener = listener
    }

    fun setTvNoteContentClickListener(listener: (Note) -> Unit) {
        tvNoteContentClickListener = listener
    }
}