package com.example.sidiay.presentation.fragments.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sidiay.R
import com.example.sidiay.databinding.FragmentSettingsBinding
import com.example.sidiay.presentation.adapters.SettingsAdapter
import com.example.sidiay.presentation.viewmodels.menu.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private val viewModel: SettingsViewModel by viewModels()
    private lateinit var binding: FragmentSettingsBinding
    private val args by navArgs<SettingsFragmentArgs>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.fSettingsRecyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(requireContext())
        binding.fSettingsRecyclerView.layoutManager = layoutManager

        val settings = listOf(
            getString(R.string.change_language),
            getString(R.string.change_theme),
            getString(R.string.change_password),)

        binding.fSettingsRecyclerView.adapter = SettingsAdapter(settings, this)
    }
}