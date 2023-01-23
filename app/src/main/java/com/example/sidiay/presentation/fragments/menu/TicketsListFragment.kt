package com.example.sidiay.presentation.fragments.menu

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
import com.example.domain.enums.ticketstates.*
import com.example.domain.models.entities.TicketEntity
import com.example.sidiay.R
import com.example.sidiay.databinding.FragmentTicketsListBinding
import com.example.sidiay.presentation.adapters.TicketsListAdapter
import com.example.sidiay.presentation.viewmodels.menu.TicketsListViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class TicketsListFragment : Fragment(R.layout.fragment_tickets_list) {
    private val viewModel: TicketsListViewModel by viewModels()
    private lateinit var binding: FragmentTicketsListBinding
    private val args: TicketsListFragmentArgs by navArgs()

    private var searchList: ArrayList<TicketEntity> = arrayListOf()

    // FILTER
    // priorities
    private val priorities: ArrayList<PriorityState> = PriorityState.values().toList() as ArrayList<PriorityState>
    private val checkedPriorities: BooleanArray = BooleanArray(priorities.size)

    // periods
    private val periods: ArrayList<PeriodState> = PeriodState.values().toList() as ArrayList<PeriodState>
    private val checkedPeriods: BooleanArray = BooleanArray(periods.size)

    // kinds
    private val kinds: ArrayList<KindState> = KindState.values().toList() as ArrayList<KindState>
    private val checkedKinds: BooleanArray = BooleanArray(kinds.size)

    // services
    private val services: ArrayList<ServiceState> = ServiceState.values().toList() as ArrayList<ServiceState>
    private val checkedServices: BooleanArray = BooleanArray(services.size)

    // statuses
    private val statuses: ArrayList<TicketStatuses> = TicketStatuses.values().toList() as ArrayList<TicketStatuses>
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
                R.id.m_home_app_bar_reset -> TODO()
                else -> false
            }
        }
    }

    private fun filter() {
        
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
            .setPositiveButton(getString(R.string.ok)) { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }

    private fun createPriorityFilterDialog() {
        createFilterDialog(
            priorities,
            checkedPriorities,
            getString(R.string.by_priority),
            ITicketStates::getName
        )
    }

    private fun createPeriodsFilterDialog() {
        createFilterDialog(
            periods,
            checkedPeriods,
            getString(R.string.by_period),
            ITicketStates::getName
        )
    }

    private fun createKindFilterDialog() {
        createFilterDialog(
            kinds,
            checkedKinds,
            getString(R.string.by_kind),
            ITicketStates::getName
        )
    }

    private fun createServiceFilterDialog() {
        createFilterDialog(
            services,
            checkedServices,
            getString(R.string.by_service),
            ITicketStates::getName
        )
    }

    private fun createStatusFilterDialog() {
        createFilterDialog(
            statuses,
            checkedStatuses,
            getString(R.string.by_status),
            ITicketStates::getName
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
        with(binding) {
            fTicketSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    fTicketSearchView.clearFocus()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (viewModel.tickets.value == null) {
                        return false
                    }

                    searchList.clear()
                    val searchText = newText!!.lowercase(Locale.getDefault())
                    if (searchText.isNotBlank()) {
                        viewModel.tickets.value?.let { itTicketList ->
                            itTicketList.forEach { itTicket ->
                                itTicket.name?.let { itName ->
                                    if (itName.lowercase().contains(searchText)) {
                                        searchList.add(itTicket)
                                    }
                                }
                            }
                        }
                    } else {
                        searchList.addAll(viewModel.tickets.value!!)
                    }
                    fTicketRecyclerView.adapter?.notifyDataSetChanged()
                    return false
                }
            })
        }
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
            val layoutManager = LinearLayoutManager(context)
            binding.fTicketRecyclerView.layoutManager = layoutManager
            binding.fTicketRecyclerView.setHasFixedSize(true)

            viewModel.tickets.value?.let {
                searchList.addAll(it)
                binding.fTicketRecyclerView.adapter = TicketsListAdapter(searchList, this)
            }
        }
    }

    private fun recyclerViewFill() {
        viewModel.fillTicketsList()
    }
}