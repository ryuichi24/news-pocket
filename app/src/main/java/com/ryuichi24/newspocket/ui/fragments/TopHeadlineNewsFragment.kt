package com.ryuichi24.newspocket.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ryuichi24.newspocket.R
import com.ryuichi24.newspocket.databinding.FragmentTopHeadlineNewsBinding
import com.ryuichi24.newspocket.models.Article
import com.ryuichi24.newspocket.models.Source
import com.ryuichi24.newspocket.ui.adapters.NewsAdapter

class TopHeadlineNewsFragment : Fragment() {

    private lateinit var newsAdapter: NewsAdapter

    // binding
    private var _binding: FragmentTopHeadlineNewsBinding? = null
    // this property only valid between onCreateView and onDestroyView
    private val binding: FragmentTopHeadlineNewsBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTopHeadlineNewsBinding.inflate(inflater, container, false)
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
        newsAdapter = NewsAdapter(emptyList() ?:emptyList())
        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL

        binding.rvTopHeadlineNews.apply {
            adapter = newsAdapter
            layoutManager = linearLayoutManager
        }
    }
}