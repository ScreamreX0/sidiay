package com.example.sidiay.presentation.fragments.menu

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.enums.states.TicketStates
import com.example.domain.enums.ticket.*
import com.example.domain.models.entities.TicketEntity
import com.example.sidiay.R
import com.example.sidiay.databinding.FragmentTicketsListBinding
import com.example.sidiay.presentation.adapters.TicketsListAdapter
import com.example.sidiay.presentation.viewmodels.menu.TicketsListViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class TicketsListFragment : Fragment(R.layout.fragment_tickets_list) {
    private val viewModel: TicketsListViewModel by viewModels()
    private lateinit var binding: FragmentTicketsListBinding
    private val args: TicketsListFragmentArgs by navArgs()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentTicketsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fTicketAddButton.setOnClickListener {
            findNavController().navigate(
                TicketsListFragmentDirections.actionFragmentTicketsToAddTicketFragment(
                    args.user
                )
            )
        }

        viewModel.tickets.observe(viewLifecycleOwner) {
            if (it == null) {
                return@observe
            }

            val layoutManager = LinearLayoutManager(context)
            binding.fTicketRecyclerView.layoutManager = layoutManager
            binding.fTicketRecyclerView.setHasFixedSize(true)

            viewModel.searchList.clear()
            viewModel.filterList.clear()
            viewModel.filterSearchList.clear()

            viewModel.searchList.addAll(it)
            viewModel.filterList.addAll(it)
            viewModel.filterSearchList.addAll(it)
            binding.fTicketRecyclerView.adapter = TicketsListAdapter(viewModel.filterSearchList, this)
        }

        viewModel.errorResult.observe(viewLifecycleOwner) {
            if (viewModel.errorResult.value == null) {
                return@observe
            }

            if (viewModel.errorResult.value!!.contains(TicketStates.NO_SERVER_CONNECTION)) {
                Toast.makeText(requireContext(), getString(R.string.no_server_connection), Toast.LENGTH_SHORT).show()
                return@observe
            }
        }

        fillRecyclerViewList()
        initSearch()
        initFilter()
    }

    private fun initFilter() {
        binding.fTicketToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.m_home_app_bar_priority -> {
                    createFilterDialog(viewModel.priorities, viewModel.checkedPriorities, getString(R.string.by_priority), ITicketEnum::getName)
                    true
                }
                R.id.m_home_app_bar_status -> {
                    createFilterDialog(viewModel.statuses, viewModel.checkedStatuses, getString(R.string.by_status), ITicketEnum::getName)
                    true
                }
                R.id.m_home_app_bar_service -> {
                    createFilterDialog(viewModel.services, viewModel.checkedServices, getString(R.string.by_service), ITicketEnum::getName)
                    true
                }
                R.id.m_home_app_bar_kind -> {
                    createFilterDialog(viewModel.kinds, viewModel.checkedKinds, getString(R.string.by_kind), ITicketEnum::getName)
                    true
                }
                R.id.m_home_app_bar_creation_date -> {
                    createFilterDialog(viewModel.periods, viewModel.checkedPeriods, getString(R.string.by_period), ITicketEnum::getName)
                    true
                }
                R.id.m_home_app_bar_expired -> TODO()
                //R.id.m_home_app_bar_my_tickets ->
                R.id.m_home_app_bar_reset -> {
                    Filter().reset()
                    true
                }
                else -> false
            }
        }

        // Filter icon
        val drawable = ContextCompat.getDrawable(
            requireContext(),
            R.drawable.ic_baseline_filter_list_24_white
        )
        binding.fTicketToolbar.overflowIcon = drawable
    }

    private fun <T> createFilterDialog(elements: ArrayList<T>, checkedElements: BooleanArray, title: String, getNameFunction: (T) -> String) {
        AlertDialog.Builder(requireContext()).setTitle(title)
            .setMultiChoiceItems(
                elements.map { getNameFunction.invoke(it) }.toTypedArray(),
                checkedElements
            ) { _, which, isChecked ->
                checkedElements[which] = isChecked
            }
            .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                Filter().filter()
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun initSearch() {
        binding.fTicketSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.fTicketSearchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (viewModel.tickets.value == null) {
                    return false
                }

                viewModel.searchList.clear()

                val searchText = newText!!.lowercase(Locale.getDefault())
                if (searchText.isBlank()) {
                    viewModel.searchList.addAll(viewModel.filterList)
                    return notifyRecyclerViewDataChanged()
                }

                viewModel.filterList.forEach { itTicket ->
                    itTicket.name?.let { itName ->
                        if (itName.lowercase().contains(searchText)) {
                            viewModel.searchList.add(itTicket)
                        }
                    }
                }

                return notifyRecyclerViewDataChanged()
            }

            @SuppressLint("NotifyDataSetChanged")
            fun notifyRecyclerViewDataChanged(): Boolean {
                viewModel.filterSearchList.clear()
                viewModel.filterSearchList.addAll(viewModel.searchList)

                binding.fTicketRecyclerView.adapter?.notifyDataSetChanged()
                return false
            }
        })
    }

    private fun fillRecyclerViewList() {
        viewModel.fillTicketsList()
    }

    private inner class Filter {
        @SuppressLint("NotifyDataSetChanged")
        fun filter() {
            if (viewModel.tickets.value == null) {
                return
            }

            viewModel.filterList.clear()
            viewModel.filterList.addAll(viewModel.tickets.value!!)

            byPriority()
            byPeriod()
            byService()
            byKind()
            byStatus()

            viewModel.filterSearchList.clear()
            viewModel.filterSearchList.addAll(viewModel.filterList)
            binding.fTicketRecyclerView.adapter?.notifyDataSetChanged()
        }

        private fun byPriority() {
            if (areAllTrue(viewModel.checkedPriorities) || areAllFalse(viewModel.checkedPriorities)) {
                return
            }

            val newList: ArrayList<TicketEntity> = arrayListOf()
            viewModel.filterList.forEach { itTicket ->
                itTicket.priority?.let {
                    for (necessaryPriority in getComparedArray(viewModel.priorities, viewModel.checkedPriorities)) {
                        if (necessaryPriority.priority.toLong() == it) {
                            newList.add(itTicket)
                        }
                    }
                }
            }
            viewModel.filterList.clear()
            viewModel.filterList.addAll(newList)
        }

        private fun byPeriod() {
            // TODO
        }

        private fun byService() {
            if (areAllTrue(viewModel.checkedServices) || areAllFalse(viewModel.checkedServices)) {
                return
            }

            val newList: ArrayList<TicketEntity> = arrayListOf()
            viewModel.filterList.forEach { itTicket ->
                itTicket.service?.let {
                    for (necessaryService in getComparedArray(viewModel.services, viewModel.checkedServices)) {
                        if (necessaryService.getName() == it) {
                            newList.add(itTicket)
                        }
                    }
                }
            }
            viewModel.filterList.clear()
            viewModel.filterList.addAll(newList)
        }

        private fun byKind() {
            if (areAllTrue(viewModel.checkedKinds) || areAllFalse(viewModel.checkedKinds)) {
                return
            }

            val newList: ArrayList<TicketEntity> = arrayListOf()
            viewModel.filterList.forEach { itTicket ->
                itTicket.kind?.let {
                    for (necessaryKind in getComparedArray(viewModel.kinds, viewModel.checkedKinds)) {
                        if (necessaryKind.id == it.id) {
                            newList.add(itTicket)
                        }
                    }
                }
            }
            viewModel.filterList.clear()
            viewModel.filterList.addAll(newList)
        }

        private fun byStatus() {
            if (areAllTrue(viewModel.checkedStatuses) || areAllFalse(viewModel.checkedStatuses)) {
                return
            }

            val newList: ArrayList<TicketEntity> = arrayListOf()
            viewModel.filterList.forEach { itTicket ->
                itTicket.status.let {
                    for (necessaryStatus in getComparedArray(viewModel.statuses, viewModel.checkedStatuses)) {
                        if (necessaryStatus.getName() == it) {
                            newList.add(itTicket)
                        }
                    }
                }
            }
            viewModel.filterList.clear()
            viewModel.filterList.addAll(newList)
        }

        @SuppressLint("NotifyDataSetChanged")
        fun reset() {
            if (viewModel.tickets.value == null) {
                return
            }

            resetBooleanArray(viewModel.checkedPriorities)
            resetBooleanArray(viewModel.checkedPeriods)
            resetBooleanArray(viewModel.checkedServices)
            resetBooleanArray(viewModel.checkedKinds)
            resetBooleanArray(viewModel.checkedStatuses)

            viewModel.filterSearchList.clear()
            viewModel.filterSearchList.addAll(viewModel.tickets.value!!)
            binding.fTicketRecyclerView.adapter?.notifyDataSetChanged()
        }
        private fun <T> getComparedArray(array: ArrayList<T>, checkedElements: BooleanArray): ArrayList<T> {
            val newArray = arrayListOf<T>()
            for (index in array.indices) {
                if (checkedElements[index]) {
                    newArray.add(array[index])
                }
            }
            return newArray
        }
        private fun areAllTrue(array: BooleanArray): Boolean {
            return !array.contains(false)
        }

        private fun areAllFalse(array: BooleanArray): Boolean {
            return !array.contains(true)
        }

        private fun resetBooleanArray(booleanArray: BooleanArray) {
            Arrays.fill(booleanArray, false)
        }
    }
}