package com.ryuichi24.newspocket.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ryuichi24.newspocket.R
import com.ryuichi24.newspocket.models.Note
import kotlinx.android.synthetic.main.item_note.view.*

class SavedNotesAdapter() : RecyclerView.Adapter<SavedNotesAdapter.SavedNotesViewHolder>() {

    inner class SavedNotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(savedNote: Note) {
            itemView.tvNoteDate.text = savedNote.date.toString()
            itemView.tvNoteContent.text = savedNote.text
        }
    }

    // setup DiffUtil
    private val differCallback = object : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
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
}