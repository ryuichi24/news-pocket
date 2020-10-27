package com.ryuichi24.newspocket.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ryuichi24.newspocket.R
import com.ryuichi24.newspocket.databinding.FragmentSavedNewsBinding
import com.ryuichi24.newspocket.models.Article
import com.ryuichi24.newspocket.ui.MainActivity
import com.ryuichi24.newspocket.ui.adapters.NewsAdapter
import com.ryuichi24.newspocket.ui.viewModels.NewsPocketViewModel

class SavedNewsFragment : Fragment() {

    private lateinit var viewModel : NewsPocketViewModel
    private lateinit var newsAdapter: NewsAdapter

    // binding
    private var _binding: FragmentSavedNewsBinding? = null
    // this property only valid between onCreateView and onDestroyView
    private val binding: FragmentSavedNewsBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSavedNewsBinding.inflate(inflater, container, false)
        // fetch viewModel
        viewModel = (activity as MainActivity).viewModel

        // start setup
        setupObservers()
        setupRecyclerView()
        setupClickListener()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // <----------------------------------------Setups---------------------------------------->
    private fun setupObservers() {
        viewModel.getSavedArticles().observe(viewLifecycleOwner, savedNewsObserver)
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL

        binding.rvSavedNews.apply {
            adapter = newsAdapter
            layoutManager = linearLayoutManager
        }
    }

    private fun setupClickListener() {
        newsAdapter.setItemClickListener { article ->
            val bundle = Bundle().apply {
                putSerializable("article", article)
            }
            findNavController().navigate(
                R.id.action_savedNewsFragment_to_newsFragment,
                bundle
            )
        }
    }

    // <----------------------------------------Observers---------------------------------------->
    private val savedNewsObserver = Observer<List<Article>> { articles ->
        newsAdapter.differ.submitList(articles)
    }
}