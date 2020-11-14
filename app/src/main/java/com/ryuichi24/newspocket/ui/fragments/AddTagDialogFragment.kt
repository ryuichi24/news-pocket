package com.ryuichi24.newspocket.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.ryuichi24.newspocket.databinding.FragmentAddTagDialogBinding
import com.ryuichi24.newspocket.models.Article
import com.ryuichi24.newspocket.utils.PutKeyConstants.CURRENT_SAVED_ARTICLE

class AddTagDialogFragment : DialogFragment() {

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
        


        return binding.root
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