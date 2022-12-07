package com.example.sidiay.presentation.fragments.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.sidiay.R
import com.example.sidiay.databinding.FragmentApplicationsBinding
import com.example.sidiay.presentation.viewmodels.menu.ApplicationFragmentViewModel

class ApplicationFragment : Fragment(R.layout.fragment_applications) {
    private val viewModel: ApplicationFragmentViewModel by viewModels()
    private lateinit var binding: FragmentApplicationsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentApplicationsBinding.inflate(inflater, container, false)
        return binding.root
    }
}