package com.ryuichi24.newspocket.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ryuichi24.newspocket.R
import com.ryuichi24.newspocket.databinding.FragmentSavedNewsBinding
import com.ryuichi24.newspocket.models.Article
import com.ryuichi24.newspocket.ui.MainActivity
import com.ryuichi24.newspocket.ui.NoteActivity
import com.ryuichi24.newspocket.ui.adapters.SavedNewsAdapter
import com.ryuichi24.newspocket.ui.viewModels.NewsPocketViewModel
import com.ryuichi24.newspocket.utils.NoteAction

class SavedNewsFragment : Fragment() {

    private lateinit var viewModel : NewsPocketViewModel
    private lateinit var savedNewsAdapter: SavedNewsAdapter

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
        setupSwipeAction()

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
        savedNewsAdapter = SavedNewsAdapter()
        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL

        binding.rvSavedNews.apply {
            adapter = savedNewsAdapter
            layoutManager = linearLayoutManager
        }
    }

    private fun setupClickListener() {
        savedNewsAdapter.setItemClickListener { article ->
            val bundle = Bundle().apply {
                putSerializable("article", article)
            }
            findNavController().navigate(
                R.id.action_savedNewsFragment_to_newsFragment,
                bundle
            )
        }

        savedNewsAdapter.setAddNoteBtnClickListener {
            val intent = Intent(requireActivity(), NoteActivity::class.java).apply {
                putExtra(NoteAction::class.simpleName, NoteAction.ADD)
            }
            startActivity(intent)
        }

        savedNewsAdapter.setReadNoteBtnItemClickListener {
            val intent = Intent(requireActivity(), NoteActivity::class.java).apply {
                putExtra(NoteAction::class.simpleName, NoteAction.READ)
            }
            startActivity(intent)
        }
    }

    private fun setupSwipeAction() {
        val itemTouchHelperCallback = object: ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.ACTION_STATE_IDLE,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val itemPosition = viewHolder.adapterPosition
                val swipedArticle = savedNewsAdapter.differ.currentList[itemPosition]

                viewModel.deleteArticle(swipedArticle)

                Snackbar.make(requireView(), "The article has been deleted", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo") {
                        viewModel.saveArticle(swipedArticle)
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rvSavedNews)
        }
    }

    // <----------------------------------------Observers---------------------------------------->
    private val savedNewsObserver = Observer<List<Article>> { articles ->
        savedNewsAdapter.differ.submitList(articles)
    }
}