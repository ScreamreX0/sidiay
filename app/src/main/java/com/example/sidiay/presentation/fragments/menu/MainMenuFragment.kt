package com.example.sidiay.presentation.fragments.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.example.sidiay.R
import com.example.sidiay.databinding.FragmentMainMenuBinding
import com.example.sidiay.presentation.viewmodels.menu.MainMenuViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainMenuFragment : Fragment(R.layout.fragment_main_menu) {
    private val viewModel: MainMenuViewModel by viewModels()
    private lateinit var binding: FragmentMainMenuBinding

    // Navigation
    private val args: MainMenuFragmentArgs by navArgs()
    private val fragmentNavController: NavController by lazy {
        (childFragmentManager.findFragmentById(R.id.f_main_fragment_container) as NavHostFragment)
            .navController
    }
    private val navOptions: NavOptions by lazy {
        val builder: NavOptions.Builder = NavOptions.Builder()
        builder.setEnterAnim(androidx.navigation.ui.R.anim.nav_default_enter_anim)
            .setPopEnterAnim(androidx.navigation.ui.R.anim.nav_default_pop_enter_anim)
            .setExitAnim(androidx.navigation.ui.R.anim.nav_default_exit_anim)
            .setPopExitAnim(androidx.navigation.ui.R.anim.nav_default_pop_exit_anim)
        builder.build()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initFragmentContainer()
        bottomNavOnClickListener()
    }

    private fun bottomNavOnClickListener() {
        binding.fMainBottomNav.setOnItemSelectedListener {
            fragmentNavController.navigate(it.itemId, args.toBundle(), navOptions)
            return@setOnItemSelectedListener true
        }
    }

    private fun initFragmentContainer() {
        fragmentNavController.navigate(R.id.nav_graph_home, args.toBundle(), navOptions)
    }
}