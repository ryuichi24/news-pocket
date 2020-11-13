package com.ryuichi24.newspocket.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.ryuichi24.newspocket.databinding.FragmentAddNoteBinding
import com.ryuichi24.newspocket.models.Note
import com.ryuichi24.newspocket.ui.MainActivity
import com.ryuichi24.newspocket.ui.NoteActivity
import com.ryuichi24.newspocket.ui.viewModels.NoteViewModel
import java.util.*

class AddNoteFragment : Fragment() {

    private lateinit var viewModel: NoteViewModel

    // binding
    private var _binding: FragmentAddNoteBinding? = null

    // accessible b/w onCreateView and onDestroyView
    private val binding: FragmentAddNoteBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        // fetch viewModel
        viewModel = (activity as NoteActivity).viewModel
        val noteId = (activity as NoteActivity).getCurrentNoteId()

        binding.addNoteBtn.setOnClickListener {
            val noteText = binding.noteContentText.editableText.toString()
            val note = Note(text = noteText, date = Date(), ownerArticleId = noteId)
            viewModel.upsertNote(note)
            binding.noteContentText.editableText.clear()
        }

        return binding.root
    }

    // clear binding
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

// <----------------------------------------Setups---------------------------------------->


// <----------------------------------------Observers---------------------------------------->

}