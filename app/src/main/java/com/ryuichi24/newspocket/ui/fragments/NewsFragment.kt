package com.ryuichi24.newspocket.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.ryuichi24.newspocket.databinding.FragmentNewsBinding

class NewsFragment : Fragment() {

    val args: NewsFragmentArgs by navArgs()

    // binding
    private var _binding: FragmentNewsBinding? = null
    // accessible b/w onCreateView and onDestroyView
    private val binding: FragmentNewsBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)

        // start setup
        setupWebView()

        return binding.root
    }

    // clear binding
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // <----------------------------------------Setups---------------------------------------->\
    private fun setupWebView() {
        val article = args.article

        binding.webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url)
        }
    }
    // <----------------------------------------Observers---------------------------------------->
}