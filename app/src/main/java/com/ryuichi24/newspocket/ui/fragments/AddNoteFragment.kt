package com.ryuichi24.newspocket.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ryuichi24.newspocket.databinding.FragmentAddNoteBinding

class AddNoteFragment : Fragment() {

    // binding
    private var _binding: FragmentAddNoteBinding? = null

    // accessible b/w onCreateView and onDestroyView
    private val binding: FragmentAddNoteBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddNoteBinding.inflate(inflater, container, false)

        // start setup


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