package com.ryuichi24.newspocket.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ryuichi24.newspocket.R
import com.ryuichi24.newspocket.databinding.FragmentSavedNewsBinding
import com.ryuichi24.newspocket.models.Article
import com.ryuichi24.newspocket.models.ArticleWithTag
import com.ryuichi24.newspocket.ui.MainActivity
import com.ryuichi24.newspocket.ui.NoteActivity
import com.ryuichi24.newspocket.ui.adapters.SavedNewsAdapter
import com.ryuichi24.newspocket.ui.viewModels.MainViewModel
import com.ryuichi24.newspocket.utils.PutKeyConstants.CURRENT_NOTE_ID
import com.ryuichi24.newspocket.utils.PutKeyConstants.NOTE_ACTION
import com.ryuichi24.newspocket.utils.NoteAction

class SavedNewsFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
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

        // fetch elements from activity
        viewModel = (activity as MainActivity).viewModel
        val fragmentManager = (activity as MainActivity).supportFragmentManager

        // start setup
        setupObservers()
        setupRecyclerView()
        setupSwipeAction()
        setupItemClickListener()
        setupBtnAddNoteClickListener()
        setupBtnTagSettingClickListener(fragmentManager)
        setupBtnReadNoteItemClickListener()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // <----------------------------------------Setups---------------------------------------->
    private fun setupObservers() {
        viewModel.getArticlesWithTag().observe(viewLifecycleOwner, savedArticlesWithTagObserver)
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

    private fun setupBtnTagSettingClickListener(fragmentManager: FragmentManager) {

        savedNewsAdapter.setBtnTagSettingClickListener { article, tagSettingBtn ->

            PopupMenu(requireContext(), tagSettingBtn).apply {
                inflate(R.menu.tag_setting_menu)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.miSelectTag -> {
                            val selectTagDialogFragment = SelectTagDialogFragment.newInstance(article)
                            selectTagDialogFragment.show(fragmentManager, tag)
                            true
                        }
                        R.id.miAddTag -> {
                            val addTagDialogFragment = AddTagDialogFragment.newInstance(article)
                            addTagDialogFragment.show(fragmentManager, tag)
                            true
                        }

                        else -> {
                            Toast.makeText(requireContext(), "Error occurred", Toast.LENGTH_SHORT)
                                .show()
                            true
                        }
                    }
                }
                show()
            }
        }

    }

    private fun setupItemClickListener() {
        savedNewsAdapter.setItemClickListener { article ->
            val bundle = Bundle().apply {
                putSerializable("article", article)
            }
            findNavController().navigate(
                R.id.action_savedNewsFragment_to_newsFragment,
                bundle
            )
        }

    }

    private fun setupBtnAddNoteClickListener() {
        savedNewsAdapter.setBtnAddNoteClickListener { article ->
            val intent = Intent(requireActivity(), NoteActivity::class.java).apply {
                putExtra(NOTE_ACTION, NoteAction.ADD)
                putExtra(CURRENT_NOTE_ID, article.articleId)
            }
            startActivity(intent)
        }
    }

    private fun setupBtnReadNoteItemClickListener() {
        savedNewsAdapter.setBtnReadNoteItemClickListener { article ->
            val intent = Intent(requireActivity(), NoteActivity::class.java).apply {
                putExtra(NOTE_ACTION, NoteAction.READ)
                putExtra(CURRENT_NOTE_ID, article.articleId)
            }
            startActivity(intent)
        }
    }

    private fun setupSwipeAction() {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
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
                val swipedArticle = savedNewsAdapter.differ.currentList[itemPosition].article

                viewModel.deleteArticle(swipedArticle)

                Snackbar.make(requireView(), "The article has been deleted", Snackbar.LENGTH_LONG)
                    .apply {
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
    private val savedArticlesWithTagObserver = Observer<List<ArticleWithTag>> {
        savedNewsAdapter.differ.submitList(it)
    }
}