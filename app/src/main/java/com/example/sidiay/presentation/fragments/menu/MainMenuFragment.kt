package com.example.sidiay.presentation.fragments.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.example.domain.models.entities.User
import com.example.domain.utils.Debugger
import com.example.sidiay.R
import com.example.sidiay.databinding.FragmentMainMenuBinding
import com.example.sidiay.presentation.fragments.start.SignInFragmentDirections
import com.example.sidiay.presentation.viewmodels.menu.MenuFragmentViewModel
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
        bottomNavOnClickListener()

        initFragmentContainer()
    }

    private fun bottomNavOnClickListener() {
        binding.fMainBottomNav.setOnItemSelectedListener {
            val builder: NavOptions.Builder = NavOptions.Builder()
            builder.setEnterAnim(androidx.navigation.ui.R.anim.nav_default_enter_anim)
                .setPopEnterAnim(androidx.navigation.ui.R.anim.nav_default_pop_enter_anim)
                .setExitAnim(androidx.navigation.ui.R.anim.nav_default_exit_anim)
                .setPopExitAnim(androidx.navigation.ui.R.anim.nav_default_pop_exit_anim)
            val options: NavOptions = builder.build()

            with(binding.fMainFragmentContainer.findNavController()) {
                when (it.itemId) {
                    R.id.nav_graph_home_nested -> {
                        navigate(R.id.nav_graph_home_nested, null, options)
                    }
                    R.id.nav_graph_settings_nested -> {
                        navigate(R.id.nav_graph_settings_nested, null, options)
                    }
                    R.id.nav_graph_notifications_nested -> {
                        navigate(R.id.nav_graph_notifications_nested, null, options)
                    }
                    R.id.nav_graph_scanner_nested -> {
                        navigate(R.id.nav_graph_scanner_nested, null, options)
                    }
                }
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun initFragmentContainer() {
        findNavController().navigate(MainMenuFragmentDirections.actionMainMenuFragmentToNavGraphHome(
            args.user
        ))
    }
}