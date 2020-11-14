package com.ryuichi24.newspocket.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.ryuichi24.newspocket.databinding.FragmentAddTagDialogBinding

class AddTagDialogFragment : DialogFragment() {

    // binding
    private var _binding: FragmentAddTagDialogBinding? = null
    // accessible b/w onCreateView and onDestroyView
    private val binding: FragmentAddTagDialogBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddTagDialogBinding.inflate(inflater, container, false)


        return binding.root
    }

}