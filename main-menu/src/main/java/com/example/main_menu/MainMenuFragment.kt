package com.example.main_menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.main_menu.databinding.FragmentMainMenuBinding

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainMenuFragment : Fragment(R.layout.fragment_main_menu) {
    private val viewModel: MainMenuViewModel by viewModels()
    private lateinit var binding: FragmentMainMenuBinding

    // Navigation
    //private val args: MainMenuFragmentArgs by navArgs() TODO
    private val fragmentNavController: NavController by lazy {
        (childFragmentManager.findFragmentById(R.id.f_main_fragment_container) as NavHostFragment).navController
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMainMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupBottomNavigation()
        initFragmentContainer()
        bottomNavOnClickListener()
    }

    private fun setupBottomNavigation() {
        binding.fMainBottomNav.setupWithNavController(fragmentNavController)
    }

    private fun bottomNavOnClickListener() {
        binding.fMainBottomNav.setOnItemSelectedListener {
            var graphId = com.example.home.R.navigation.home_graph
            when (it.itemId) {
                R.id.scanner_graph -> graphId = com.example.scanner.R.navigation.scanner_graph
                R.id.notifications_graph -> graphId = com.example.notifications.R.navigation.notifications_graph
                R.id.settings_graph -> graphId = com.example.settings.R.navigation.settings_graph
            }
            fragmentNavController.setGraph(graphId)

            //fragmentNavController.navigate(it.itemId, args.toBundle(), navOptions)
            //fragmentNavController.navigate(it.itemId, null, navOptions)

            return@setOnItemSelectedListener true
        }
    }

    private fun initFragmentContainer() {
        // fragmentNavController.navigate(coreR.id.nav_graph_home, args.toBundle(), navOptions) TODO
    }
}