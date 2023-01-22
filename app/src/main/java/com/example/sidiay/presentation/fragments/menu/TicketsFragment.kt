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
import com.example.domain.enums.*
import com.example.domain.models.entities.TicketEntity
import com.example.sidiay.R
import com.example.sidiay.databinding.FragmentTicketsBinding
import com.example.sidiay.presentation.adapters.TicketsAdapter
import com.example.sidiay.presentation.viewmodels.menu.TicketsFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class TicketsFragment : Fragment(R.layout.fragment_tickets) {
    private val viewModel: TicketsFragmentViewModel by viewModels()
    private lateinit var binding: FragmentTicketsBinding
    private val args: TicketsFragmentArgs by navArgs()

    private var searchList: ArrayList<TicketEntity> = arrayListOf()

    // FILTER
    // priorities
    private val priorities: ArrayList<PriorityState> = arrayListOf(
        PriorityState.Urgent,
        PriorityState.High,
        PriorityState.Medium,
        PriorityState.Low,
        PriorityState.VeryLow,
    )
    private val checkedPriorities: BooleanArray = BooleanArray(priorities.size)

    // periods
    private val periods: ArrayList<PeriodState> = arrayListOf(
        PeriodState.Day,
        PeriodState.ThreeDays,
        PeriodState.Week,
        PeriodState.Month,
        PeriodState.AllTime,
    )
    private val checkedPeriods: BooleanArray = BooleanArray(periods.size)

    // kinds
    private val kinds: ArrayList<KindState> = arrayListOf(
        KindState.Plane,
        KindState.Current,
        KindState.Capital,
        KindState.TO,
        KindState.PPR,
        KindState.Watered,
        KindState.SlingingWork,
    )
    private val checkedKinds: BooleanArray = BooleanArray(kinds.size)

    // services
    private val services: ArrayList<ServiceState> = arrayListOf(
        ServiceState.NPO,
        ServiceState.Energo,
        ServiceState.KIP,
        ServiceState.Welding,
        ServiceState.PRS,
        ServiceState.Research,
        ServiceState.ConstructionWorks,
    )
    private val checkedServices: BooleanArray = BooleanArray(services.size)

    // statuses
    private val statuses: ArrayList<TicketStates> = arrayListOf(
        TicketStates.NotFormed,
        TicketStates.New,
        TicketStates.Accepted,
        TicketStates.Denied,
        TicketStates.Paused,
        TicketStates.Done,
        TicketStates.Closed,
        TicketStates.ForRevision,
    )
    private val checkedStatuses: BooleanArray = BooleanArray(services.size)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTicketsBinding.inflate(inflater, container, false)
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
                    createCreationDateFilterDialog()
                    true
                }
                R.id.m_home_app_bar_expired -> TODO()
                //R.id.m_home_app_bar_my_tickets ->
                R.id.m_home_app_bar_reset -> TODO()
                else -> false
            }
        }
    }

    private fun createPriorityFilterDialog() {
        val builder = AlertDialog.Builder(requireContext())

        builder.setTitle(getString(R.string.by_priority))
            .setMultiChoiceItems(
                priorities.map { it.name }.toTypedArray(),
                checkedPriorities
            ) { _, which, isChecked ->
                checkedPriorities[which] = isChecked
            }
            .setPositiveButton(getString(R.string.ok)) { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }

    private fun createCreationDateFilterDialog() {
        val builder = AlertDialog.Builder(requireContext())

        builder.setTitle(getString(R.string.by_creation_date))
            .setMultiChoiceItems(
                periods.map { it.name }.toTypedArray(),
                checkedPeriods
            ) { _, which, isChecked ->
                checkedPeriods[which] = isChecked
            }
            .setPositiveButton(getString(R.string.ok)) { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }

    private fun createKindFilterDialog() {
        val builder = AlertDialog.Builder(requireContext())

        builder.setTitle(getString(R.string.by_kind))
            .setMultiChoiceItems(
                kinds.map { it.name }.toTypedArray(),
                checkedKinds
            ) { _, which, isChecked ->
                checkedKinds[which] = isChecked
            }
            .setPositiveButton(getString(R.string.ok)) { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }

    private fun createServiceFilterDialog() {
        val builder = AlertDialog.Builder(requireContext())

        builder.setTitle(getString(R.string.by_service))
            .setMultiChoiceItems(
                services.map { it.name }.toTypedArray(),
                checkedServices
            ) { _, which, isChecked ->
                checkedServices[which] = isChecked
            }
            .setPositiveButton(getString(R.string.ok)) { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }

    private fun createStatusFilterDialog() {
        val builder = AlertDialog.Builder(requireContext())

        builder.setTitle(getString(R.string.by_status))
            .setMultiChoiceItems(
                statuses.map { it.name }.toTypedArray(),
                checkedStatuses
            ) { _, which, isChecked ->
                checkedStatuses[which] = isChecked
            }
            .setPositiveButton(getString(R.string.ok)) { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
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
                TicketsFragmentDirections.actionFragmentTicketsToAddTicketFragment(
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
                binding.fTicketRecyclerView.adapter = TicketsAdapter(searchList, this)
            }
        }
    }

    private fun recyclerViewFill() {
        viewModel.fillTicketsList()
    }
}