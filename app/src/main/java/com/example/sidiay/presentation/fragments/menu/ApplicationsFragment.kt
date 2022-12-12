package com.example.sidiay.presentation.fragments.menu

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sidiay.R
import com.example.sidiay.databinding.FragmentApplicationsBinding
import com.example.sidiay.presentation.adapters.ApplicationsAdapter
import com.example.sidiay.presentation.viewmodels.menu.ApplicationFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ApplicationsFragment : Fragment(R.layout.fragment_applications) {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerViewObserver()
        fillRecyclerView()

        handleAddButton()
    }

    private fun handleAddButton() {
        binding.fApplicationAddButton.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_applications_to_addApplicationFragment)
        }
    }

    @SuppressLint("SimpleDateFormat", "NewApi")
    private fun initRecyclerViewObserver() {
        viewModel.applications.observe(viewLifecycleOwner) {
            val layoutManager = LinearLayoutManager(context)
            binding.fApplicationRecyclerView.layoutManager = layoutManager
            binding.fApplicationRecyclerView.setHasFixedSize(true)

            binding.fApplicationRecyclerView.adapter = viewModel.applications
                .value?.let { applications -> ApplicationsAdapter(applications, this) }
        }
    }

    private fun fillRecyclerView() {
        viewModel.fillApplicationsList()
    }
}