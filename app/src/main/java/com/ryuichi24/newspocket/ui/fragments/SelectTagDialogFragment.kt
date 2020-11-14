package com.ryuichi24.newspocket.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.ryuichi24.newspocket.databinding.FragmentSelectTagDialogBinding

class SelectTagDialogFragment : DialogFragment() {

    // binding
    private var _binding: FragmentSelectTagDialogBinding? = null
    // accessible b/w onCreateView and onDestroyView
    private val binding: FragmentSelectTagDialogBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSelectTagDialogBinding.inflate(inflater, container, false)


        return binding.root
    }

}