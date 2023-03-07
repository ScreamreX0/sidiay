package com.example.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.settings.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint
import com.example.core.R as coreR

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private val viewModel: SettingsViewModel by viewModels()
    private lateinit var binding: FragmentSettingsBinding
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
            getString(coreR.string.change_language),
            getString(coreR.string.change_theme),
            getString(coreR.string.change_password),)

        binding.fSettingsRecyclerView.adapter = SettingsAdapter(settings, this)
    }
}