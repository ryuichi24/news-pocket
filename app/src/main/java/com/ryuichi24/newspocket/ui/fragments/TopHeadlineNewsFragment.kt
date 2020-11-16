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
import com.ryuichi24.newspocket.databinding.FragmentTopHeadlineNewsBinding
import com.ryuichi24.newspocket.models.NewsResponse
import com.ryuichi24.newspocket.ui.MainActivity
import com.ryuichi24.newspocket.ui.adapters.NewsAdapter
import com.ryuichi24.newspocket.ui.viewModels.MainViewModel

class TopHeadlineNewsFragment : Fragment() {

    private lateinit var viewModel : MainViewModel
    private lateinit var newsAdapter: NewsAdapter

    // binding
    private var _binding: FragmentTopHeadlineNewsBinding? = null
    // this property only valid between onCreateView and onDestroyView
    private val binding: FragmentTopHeadlineNewsBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTopHeadlineNewsBinding.inflate(inflater, container, false)

        // fetch viewModel
        viewModel = (activity as MainActivity).viewModel

        // start setup
        setupObservers()
        setupRecyclerView()
        setupClickListener()

        return binding.root
    }

    // clear binding
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // <----------------------------------------Setups---------------------------------------->
    private fun setupObservers() {
        viewModel.topHeadlineNews.observe(viewLifecycleOwner, newsObserver)
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL

        binding.rvTopHeadlineNews.apply {
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
                R.id.action_topHeadlineNewsFragment_to_newsFragment,
                bundle
            )
        }
    }

    // <----------------------------------------Observers---------------------------------------->
    private val newsObserver = Observer<NewsResponse> {
        // TODO: add response validation
        newsAdapter.differ.submitList(it.articles)
    }
}