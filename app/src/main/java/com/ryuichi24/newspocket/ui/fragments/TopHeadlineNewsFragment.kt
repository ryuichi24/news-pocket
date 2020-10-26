package com.ryuichi24.newspocket.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ryuichi24.newspocket.databinding.FragmentTopHeadlineNewsBinding
import com.ryuichi24.newspocket.models.NewsResponse
import com.ryuichi24.newspocket.ui.adapters.NewsAdapter
import com.ryuichi24.newspocket.ui.viewModels.NewsPocketViewModel
import com.ryuichi24.newspocket.utils.DependencyInjection

class TopHeadlineNewsFragment : Fragment() {

    private lateinit var viewModel : NewsPocketViewModel
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
        setupViewModel()
        setupRecyclerView()

        // done
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            DependencyInjection.provideViewModelFactory()
        ).get(NewsPocketViewModel::class.java)

        // set observers
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

    // observers
    private val newsObserver = Observer<NewsResponse> {
        // TODO: add response validation
        newsAdapter.differ.submitList(it.articles)
    }
}