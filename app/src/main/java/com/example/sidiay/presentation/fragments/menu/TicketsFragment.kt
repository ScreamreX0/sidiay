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
import com.example.domain.enums.Priorities
import com.example.domain.models.entities.Ticket
import com.example.domain.utils.Debugger
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

    private var searchList: ArrayList<Ticket> = arrayListOf()

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

        binding.fTicketToolbar.setOnMenuItemClickListener{
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