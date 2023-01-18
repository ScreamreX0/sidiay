package com.example.sidiay.presentation.fragments.menu

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.enums.Priorities
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

    // Filter
    private val priorities: ArrayList<Priorities> = arrayListOf(
        Priorities.Urgent,
        Priorities.High,
        Priorities.Medium,
        Priorities.Low,
        Priorities.VeryLow,
    )
    private val checkedPriorities: BooleanArray = BooleanArray(priorities.size)

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

        initFilter()
    }

    private fun initFilter() {
        initFilterIcon()

        binding.fApplicationToolbar.setOnMenuItemClickListener{
            when (it.itemId) {
                R.id.m_home_app_bar_priority -> {
                    createPriorityFilterDialog()
                    true
                }
                R.id.m_home_app_bar_status -> {
                    createStatusFilterDialog()
                    true
                }
                R.id.m_home_app_bar_service -> {
                    createServiceFilterDialog()
                    true
                }
                R.id.m_home_app_bar_kind -> {
                    createKindFilterDialog()
                    true
                }
                R.id.m_home_app_bar_creation_date -> {
                    createCreationDateFilterDialog()
                    true
                }
                R.id.m_home_app_bar_expired -> TODO()
                //R.id.m_home_app_bar_my_applications ->
                R.id.m_home_app_bar_reset -> TODO()
                else -> false
            }
        }
    }

    private fun createCreationDateFilterDialog() {
        TODO("Not yet implemented")
    }

    private fun createKindFilterDialog() {
        TODO("Not yet implemented")
    }

    private fun createServiceFilterDialog() {
        TODO("Not yet implemented")
    }

    private fun createStatusFilterDialog() {
        TODO("Not yet implemented")
    }

    private fun createPriorityFilterDialog() {
        val builder = AlertDialog.Builder(requireContext())

        builder.setTitle(getString(R.string.by_priority))
            .setMultiChoiceItems(
                priorities.map { it.name }.toTypedArray(),
                checkedPriorities
            ) { dialog, which, isChecked ->
                checkedPriorities[which] = isChecked
                val currentItem = priorities[which]
                Debugger.printInfo(currentItem.name)
            }
            .setPositiveButton(getString(R.string.ok)) {dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }

    private fun initFilterIcon() {
        val drawable = ContextCompat.getDrawable(
            requireContext(),
            R.drawable.ic_baseline_filter_list_24_white
        )
        binding.fApplicationToolbar.overflowIcon = drawable
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
            findNavController().navigate(
                ApplicationsFragmentDirections.actionFragmentApplicationsToAddApplicationFragment(
                    args.user
                )
            )
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