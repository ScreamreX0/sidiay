package com.example.sidiay.presentation.fragments.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.models.entities.Application
import com.example.domain.utils.Debugger
import com.example.sidiay.R
import com.example.sidiay.databinding.FragmentApplicationsBinding
import com.example.sidiay.presentation.adapters.ApplicationsAdapter
import com.example.sidiay.presentation.viewmodels.menu.ApplicationsFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ApplicationsFragment : Fragment(R.layout.fragment_applications) {
    private val viewModel: ApplicationsFragmentViewModel by viewModels()
    private lateinit var binding: FragmentApplicationsBinding
    private val args: ApplicationsFragmentArgs by navArgs()

    private var searchList: ArrayList<Application> = arrayListOf()

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

        initRecyclerView()
        recyclerViewFill()

        addButtonHandler()

        initSearch()
    }

    private fun initSearch() {
        with(binding) {
            fApplicationSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    fApplicationSearchView.clearFocus()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (viewModel.applications.value == null) {
                        return false
                    }

                    searchList.clear()
                    val searchText = newText!!.lowercase(Locale.getDefault())
                    if (searchText.isNotBlank()) {
                        Debugger.printInfo("search is not blank", "DEBUG")
                        viewModel.applications.value!!.forEach {
                            if (it.title.lowercase().contains(searchText)) {
                                searchList.add(it)
                                Debugger.printInfo("${it.title} added", "DEBUG")
                            } else {
                                Debugger.printInfo("${it.title} != $searchText", "DEBUG")
                            }
                        }
                    } else {
                        searchList.addAll(viewModel.applications.value!!)
                    }

                    Debugger.printInfo("", "DEBUG")
                    fApplicationRecyclerView.adapter?.notifyDataSetChanged()
                    return false
                }
            })
        }
    }

    private fun addButtonHandler() {
        binding.fApplicationAddButton.setOnClickListener {
            findNavController().navigate(ApplicationsFragmentDirections.actionFragmentApplicationsToAddApplicationFragment(
                args.user
            ))
        }
    }

    private fun initRecyclerView() {
        viewModel.applications.observe(viewLifecycleOwner) {
            val layoutManager = LinearLayoutManager(context)
            binding.fApplicationRecyclerView.layoutManager = layoutManager
            binding.fApplicationRecyclerView.setHasFixedSize(true)

            viewModel.applications.value?.let {
                searchList.addAll(it)
                binding.fApplicationRecyclerView.adapter = ApplicationsAdapter(searchList, this)
            }
        }
    }

    private fun recyclerViewFill() {
        viewModel.fillApplicationsList()
    }
}