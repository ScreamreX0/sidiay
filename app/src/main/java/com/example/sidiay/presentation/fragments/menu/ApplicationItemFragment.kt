package com.example.sidiay.presentation.fragments.menu

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.sidiay.R
import com.example.sidiay.databinding.FragmentApplicationItemBinding
import com.example.sidiay.presentation.viewmodels.menu.ApplicationItemViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ApplicationItemFragment : Fragment(R.layout.fragment_application_item) {
    private val viewModel: ApplicationItemViewModel by viewModels()
    private lateinit var binding: FragmentApplicationItemBinding
    private val args by navArgs<ApplicationItemFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentApplicationItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.println(Log.INFO, "Args", args.application.id.toString())
    }
}