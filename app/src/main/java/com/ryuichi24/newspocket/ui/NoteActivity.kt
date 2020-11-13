package com.ryuichi24.newspocket.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ryuichi24.newspocket.R
import com.ryuichi24.newspocket.ui.viewModels.NoteViewModel
import com.ryuichi24.newspocket.utils.DependencyProvider
import com.ryuichi24.newspocket.utils.IntentConstants.CURRENT_NOTE_ID
import com.ryuichi24.newspocket.utils.IntentConstants.NOTE_ACTION
import com.ryuichi24.newspocket.utils.NoteAction


class NoteActivity : AppCompatActivity() {

    lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        setupBottomNavigation()
        setupViewModel()
    }

    fun getCurrentNoteId() : Int {
        return intent.getIntExtra(CURRENT_NOTE_ID, 0)
    }

    // <----------------------------------------Setups---------------------------------------->
    private fun setupBottomNavigation() {
        val fragmentContainer = findViewById<FragmentContainerView>(R.id.fragmentContainer)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        bottomNavigationView.setupWithNavController(fragmentContainer.findNavController())

        val graphInflater = fragmentContainer.findNavController().navInflater
        val navGraph = graphInflater.inflate(R.navigation.note_navigation_graph)
        val navController = fragmentContainer.findNavController()

        val whichAction = intent.getSerializableExtra(NOTE_ACTION)
        val destination: Int

        destination = when (whichAction) {
            NoteAction.ADD -> R.id.addNoteFragment
            NoteAction.READ -> R.id.noteListFragment
            else -> 0
        }

        navGraph.startDestination = destination
        navController.graph = navGraph
    }

    private fun setupViewModel() {
        viewModel = DependencyProvider.provideNoteViewModel(this)
    }




}