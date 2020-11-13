package com.ryuichi24.newspocket.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.ryuichi24.newspocket.databinding.FragmentNoteListBinding
import com.ryuichi24.newspocket.models.Note
import com.ryuichi24.newspocket.ui.NoteActivity
import com.ryuichi24.newspocket.ui.adapters.SavedNewsAdapter
import com.ryuichi24.newspocket.ui.adapters.SavedNotesAdapter
import com.ryuichi24.newspocket.ui.viewModels.NoteViewModel

class NoteListFragment : Fragment() {

    private lateinit var viewModel : NoteViewModel
    private lateinit var savedNotesAdapter: SavedNotesAdapter
    // binding
    private var _binding: FragmentNoteListBinding? = null

    // accessible b/w onCreateView and onDestroyView
    private val binding: FragmentNoteListBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteListBinding.inflate(inflater, container, false)

        // fetch elements from activity
        viewModel = (activity as NoteActivity).viewModel
        val currentNoteId = (activity as NoteActivity).getCurrentNoteId()

        // start setup
        setupRecyclerView()
        setupObservers(currentNoteId)
        setupClickListener()

        return binding.root
    }

    // clear binding
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

// <----------------------------------------Setups---------------------------------------->

    private fun setupRecyclerView() {
        savedNotesAdapter = SavedNotesAdapter()
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        gridLayoutManager.orientation = GridLayoutManager.VERTICAL

        binding.rvNoteList.apply {
            adapter = savedNotesAdapter
            layoutManager = gridLayoutManager
        }
    }

    private fun setupObservers(noteId: Int) {
        viewModel.getNotesByArticleId(noteId).observe(viewLifecycleOwner, savedNotesObserver)
    }

    private fun setupClickListener() {
        savedNotesAdapter.setDeleteBtnClickListener { note ->
            viewModel.deleteNote(note)

            Snackbar.make(requireView(), "The note has been deleted", Snackbar.LENGTH_LONG)
                .apply {
                    setAction("Undo") {
                        viewModel.upsertNote(note)
                    }
                    show()
                }
        }
    }

// <----------------------------------------Observers---------------------------------------->
    private val savedNotesObserver = Observer<List<Note>> { notes ->
            savedNotesAdapter.differ.submitList(notes)
    }
}