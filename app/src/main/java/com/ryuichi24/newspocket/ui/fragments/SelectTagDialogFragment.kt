package com.ryuichi24.newspocket.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.ryuichi24.newspocket.R
import com.ryuichi24.newspocket.databinding.FragmentSelectTagDialogBinding
import com.ryuichi24.newspocket.models.Article
import com.ryuichi24.newspocket.ui.MainActivity
import com.ryuichi24.newspocket.ui.viewModels.NewsPocketViewModel
import com.ryuichi24.newspocket.utils.PutKeyConstants.CURRENT_SAVED_ARTICLE

class SelectTagDialogFragment : DialogFragment() {

    private lateinit var viewModel: NewsPocketViewModel

    private var currentSavedArticle: Article? = null

    // binding
    private var _binding: FragmentSelectTagDialogBinding? = null

    // accessible b/w onCreateView and onDestroyView
    private val binding: FragmentSelectTagDialogBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            currentSavedArticle = it.getSerializable(CURRENT_SAVED_ARTICLE) as Article
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSelectTagDialogBinding.inflate(inflater, container, false)

        viewModel = (activity as MainActivity).viewModel

        // start setup
        setupSpinner()


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupSpinner() {
        val customList = listOf<String>("First", "Second", "Third")
        val adapter = ArrayAdapter<String>(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            customList
        )
        binding.spiTag.adapter = adapter

        binding.spiTag.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(
                    requireContext(),
                    "You selected ${parent?.getItemAtPosition(position).toString()}",
                    Toast.LENGTH_LONG
                ).show()
            }

        }

    }

    companion object {
        @JvmStatic
        fun newInstance(currentSavedArticle: Article) =
            SelectTagDialogFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(CURRENT_SAVED_ARTICLE, currentSavedArticle)
                }
            }
    }

}