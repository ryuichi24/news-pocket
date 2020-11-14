package com.ryuichi24.newspocket.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
import com.ryuichi24.newspocket.databinding.FragmentAddTagDialogBinding
import com.ryuichi24.newspocket.models.Article
import com.ryuichi24.newspocket.models.Tag
import com.ryuichi24.newspocket.ui.MainActivity
import com.ryuichi24.newspocket.ui.viewModels.NewsPocketViewModel
import com.ryuichi24.newspocket.utils.PutKeyConstants.CURRENT_SAVED_ARTICLE

class AddTagDialogFragment : DialogFragment() {

    private lateinit var viewModel : NewsPocketViewModel

    private var currentSavedArticle: Article? = null
    // binding
    private var _binding: FragmentAddTagDialogBinding? = null
    // accessible b/w onCreateView and onDestroyView
    private val binding: FragmentAddTagDialogBinding get() = _binding!!

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
        _binding = FragmentAddTagDialogBinding.inflate(inflater, container, false)

        // fetch elements from activity
        viewModel = (activity as MainActivity).viewModel

        // start setuo
        setupAddTagBtn()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupAddTagBtn() {
        val tagName = binding.etTagName.editableText.toString()
        binding.btnAddTag.setOnClickListener {
            viewModel.addTag(Tag(name = tagName))
            binding.etTagName.editableText.clear()
            Snackbar.make(requireView(), "The tag has been added", Snackbar.LENGTH_LONG).show()
        }

    }

    companion object {
        @JvmStatic
        fun newInstance(currentSavedArticle: Article) =
            AddTagDialogFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(CURRENT_SAVED_ARTICLE, currentSavedArticle)
                }
            }
    }

}