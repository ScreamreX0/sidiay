package com.example.sidiay.presentation.fragments.menu

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.enums.ticket.*
import com.example.domain.models.entities.TicketEntity
import com.example.domain.utils.Debugger
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



    // FILTER
    // priorities
    private val priorities: ArrayList<TicketPriorityEnum> = TicketPriorityEnum.values().toList() as ArrayList<TicketPriorityEnum>
    private val checkedPriorities: BooleanArray = BooleanArray(priorities.size)

    // periods
    private val periods: ArrayList<TicketPeriodEnum> = TicketPeriodEnum.values().toList() as ArrayList<TicketPeriodEnum>
    private val checkedPeriods: BooleanArray = BooleanArray(periods.size)

    // kinds
    private val kinds: ArrayList<TicketKindEnum> = TicketKindEnum.values().toList() as ArrayList<TicketKindEnum>
    private val checkedKinds: BooleanArray = BooleanArray(kinds.size)

    // services
    private val services: ArrayList<TicketServiceEnum> = TicketServiceEnum.values().toList() as ArrayList<TicketServiceEnum>
    private val checkedServices: BooleanArray = BooleanArray(services.size)

    // statuses
    private val statuses: ArrayList<TicketStatusEnum> = TicketStatusEnum.values().toList() as ArrayList<TicketStatusEnum>
    private val checkedStatuses: BooleanArray = BooleanArray(statuses.size)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentTicketsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerViewObserver()
        recyclerViewFill()
        addButtonHandler()
        initSearch()
        initFilter()
    }

    private fun initFilter() {
        initFilterIcon()

        binding.fTicketToolbar.setOnMenuItemClickListener {
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
                    createPeriodsFilterDialog()
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
    }

    private fun <T> createFilterDialog(elements: ArrayList<T>, checkedElements: BooleanArray, title: String, getNameFunction: (T) -> String) {
        val builder = AlertDialog.Builder(requireContext())

        builder.setTitle(title)
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

    private fun createPriorityFilterDialog() {
        createFilterDialog(
            priorities,
            checkedPriorities,
            getString(R.string.by_priority),
            ITicketEnum::getName
        )
    }

    private fun createPeriodsFilterDialog() {
        createFilterDialog(
            periods,
            checkedPeriods,
            getString(R.string.by_period),
            ITicketEnum::getName
        )
    }

    private fun createKindFilterDialog() {
        createFilterDialog(
            kinds,
            checkedKinds,
            getString(R.string.by_kind),
            ITicketEnum::getName
        )
    }

    private fun createServiceFilterDialog() {
        createFilterDialog(
            services,
            checkedServices,
            getString(R.string.by_service),
            ITicketEnum::getName
        )
    }

    private fun createStatusFilterDialog() {
        createFilterDialog(
            statuses,
            checkedStatuses,
            getString(R.string.by_status),
            ITicketEnum::getName
        )
    }

    private fun initFilterIcon() {
        val drawable = ContextCompat.getDrawable(
            requireContext(),
            R.drawable.ic_baseline_filter_list_24_white
        )
        binding.fTicketToolbar.overflowIcon = drawable
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
                    viewModel.searchList.addAll(viewModel.filteredList)
                    return notifyRecyclerViewDataChanged()
                }

                viewModel.filteredList.forEach { itTicket ->
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
                viewModel.filteredSearchedList.clear()
                viewModel.filteredSearchedList.addAll(viewModel.searchList)

                binding.fTicketRecyclerView.adapter?.notifyDataSetChanged()
                return false
            }
        })
    }

    private fun addButtonHandler() {
        binding.fTicketAddButton.setOnClickListener {
            findNavController().navigate(
                TicketsListFragmentDirections.actionFragmentTicketsToAddTicketFragment(
                    args.user
                )
            )
        }
    }

    private fun initRecyclerViewObserver() {
        viewModel.tickets.observe(viewLifecycleOwner) {
            if (it == null) {
                return@observe
            }

            val layoutManager = LinearLayoutManager(context)
            binding.fTicketRecyclerView.layoutManager = layoutManager
            binding.fTicketRecyclerView.setHasFixedSize(true)

            viewModel.searchList.clear()
            viewModel.filteredList.clear()
            viewModel.filteredSearchedList.clear()

            viewModel.searchList.addAll(it)
            viewModel.filteredList.addAll(it)
            viewModel.filteredSearchedList.addAll(it)
            binding.fTicketRecyclerView.adapter = TicketsListAdapter(viewModel.filteredSearchedList, this)
        }
    }

    private fun recyclerViewFill() {
        viewModel.fillTicketsList()
    }

    private inner class Filter {
        @SuppressLint("NotifyDataSetChanged")
        fun filter() {
            if (viewModel.tickets.value == null) {
                return
            }

            viewModel.filteredList.clear()
            viewModel.filteredList.addAll(viewModel.tickets.value!!)


            byPriority(priorities, checkedPriorities)


            viewModel.filteredSearchedList.clear()
            viewModel.filteredSearchedList.addAll(viewModel.filteredList)
            binding.fTicketRecyclerView.adapter?.notifyDataSetChanged()
        }

        @SuppressLint("NotifyDataSetChanged")
        fun reset() {
            if (viewModel.tickets.value == null) {
                return
            }

            viewModel.filteredSearchedList.clear()
            viewModel.filteredSearchedList.addAll(viewModel.tickets.value!!)
            binding.fTicketRecyclerView.adapter?.notifyDataSetChanged()
        }

        private fun byPriority(
            priorities: ArrayList<TicketPriorityEnum>,
            checkedPriorities: BooleanArray
        ): Filter {
            val comparedArray = getComparedArray(priorities, checkedPriorities)
            Debugger.printInfo("Compared priorities array: ${comparedArray.joinToString { it.name }}")

            val newList: ArrayList<TicketEntity> = arrayListOf()
            viewModel.filteredList.forEach { itTicket ->
                itTicket.priority.let {
                    for (necessaryPriority in comparedArray) {
                        if (necessaryPriority.priority.toLong() == itTicket.priority) {
                            newList.add(itTicket)
                        }
                    }
                }
            }
            Debugger.printInfo("New filtered by priorities list:${newList.joinToString { it.name.toString() }}")
            viewModel.filteredList.clear()
            viewModel.filteredList.addAll(newList)
            return this
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
    }
}