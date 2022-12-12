package com.example.sidiay.presentation.fragments.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.sidiay.R
import com.example.sidiay.databinding.FragmentAddApplicationBinding
import com.example.sidiay.presentation.viewmodels.menu.AddApplicationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddApplicationFragment : Fragment(R.layout.fragment_add_application) {
    private val viewModel: AddApplicationViewModel by viewModels()
    lateinit var binding: FragmentAddApplicationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddApplicationBinding.inflate(inflater, container, false)

        setupServicesAdapter()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backButtonHandler()
    }

    private fun backButtonHandler() {
        binding.fAddApplicationBackButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupServicesAdapter() {
        val services = listOf("test", "test2", "test3")
        val adapter =
            ArrayAdapter(requireContext(), R.layout.item_add_application_service, services)
        binding.fAddApplicationAutoCompleteService.setAdapter(adapter)
    }
}