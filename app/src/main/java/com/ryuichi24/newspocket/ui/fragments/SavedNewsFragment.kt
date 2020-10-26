package com.ryuichi24.newspocket.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ryuichi24.newspocket.databinding.FragmentSavedNewsBinding
import com.ryuichi24.newspocket.ui.adapters.NewsAdapter

class SavedNewsFragment : Fragment() {

    private lateinit var newsAdapter: NewsAdapter

    // binding
    private var _binding: FragmentSavedNewsBinding? = null
    // this property only valid between onCreateView and onDestroyView
    private val binding: FragmentSavedNewsBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSavedNewsBinding.inflate(inflater, container, false)
        // start

        setupRecyclerView()

        // done
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        // TODO: inject articles from ViewModel
        newsAdapter = NewsAdapter()
        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL

        binding.rvSavedNews.apply {
            adapter = newsAdapter
            layoutManager = linearLayoutManager
        }
    }
}