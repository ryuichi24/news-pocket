package com.ryuichi24.newspocket.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
import com.ryuichi24.newspocket.databinding.FragmentEditNoteDialogBinding
import com.ryuichi24.newspocket.models.Note
import com.ryuichi24.newspocket.ui.NoteActivity
import com.ryuichi24.newspocket.ui.viewModels.NoteViewModel
import com.ryuichi24.newspocket.utils.PutKeyConstants.CURRENT_NOTE
import java.util.*

class EditNoteDialogFragment : DialogFragment() {

    private lateinit var viewModel: NoteViewModel

    private var currentNote: Note? = null

    // binding
    private var _binding: FragmentEditNoteDialogBinding? = null

    // accessible b/w onCreateView and onDestroyView
    private val binding: FragmentEditNoteDialogBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            currentNote = it.getSerializable(CURRENT_NOTE) as Note
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditNoteDialogBinding.inflate(inflater, container, false)

        // fetch elements from activity
        viewModel = (activity as NoteActivity).viewModel

        // start setup
        setupEditNoteBtn()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupEditNoteBtn() {
        binding.etNoteEditContent.editableText.append(currentNote?.text)

        binding.btnNoteEdit.setOnClickListener {
            val newNoteContent = binding.etNoteEditContent.editableText.toString()
            val newNote = currentNote?.copy(text = newNoteContent, date = Date())
            newNote?.noteId = currentNote?.noteId
            viewModel.updateNote(newNote!!)
            Snackbar.make(requireView(), "The note has been updated", Snackbar.LENGTH_LONG).show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(currentNote: Note) =
            EditNoteDialogFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(CURRENT_NOTE, currentNote)
                }
            }
    }

}