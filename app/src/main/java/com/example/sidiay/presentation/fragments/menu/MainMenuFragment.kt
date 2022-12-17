package com.example.sidiay.presentation.fragments.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.example.sidiay.R
import com.example.sidiay.databinding.FragmentMainMenuBinding
import com.example.sidiay.presentation.viewmodels.menu.MenuFragmentViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainMenuFragment : Fragment(R.layout.fragment_main_menu) {
    private val viewModel: MenuFragmentViewModel by viewModels()
    private lateinit var binding: FragmentMainMenuBinding
    private val args: MainMenuFragmentArgs by navArgs()

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
        val navController =
            (childFragmentManager.findFragmentById(R.id.f_main_fragment_container) as NavHostFragment)
                .navController

        binding.fMainBottomNav.setOnItemSelectedListener {
            val builder: NavOptions.Builder = NavOptions.Builder()
            builder.setEnterAnim(androidx.navigation.ui.R.anim.nav_default_enter_anim)
                .setPopEnterAnim(androidx.navigation.ui.R.anim.nav_default_pop_enter_anim)
                .setExitAnim(androidx.navigation.ui.R.anim.nav_default_exit_anim)
                .setPopExitAnim(androidx.navigation.ui.R.anim.nav_default_pop_exit_anim)
            val options: NavOptions = builder.build()

            navController.navigate(it.itemId, args.toBundle(), options)

            return@setOnItemSelectedListener true
        }
    }

    private fun initFragmentContainer() {
        return
        findNavController().navigate(
            MainMenuFragmentDirections.actionMainMenuFragmentToNavGraphHome(
                args.user
            )
        )
    }
}