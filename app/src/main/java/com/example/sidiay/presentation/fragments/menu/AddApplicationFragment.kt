package com.example.sidiay.presentation.fragments.menu

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.example.sidiay.R
import com.example.sidiay.databinding.FragmentAddApplicationBinding
import com.example.sidiay.presentation.viewmodels.menu.AddApplicationViewModel
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class AddApplicationFragment : Fragment(R.layout.fragment_add_application),
    DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private val viewModel: AddApplicationViewModel by viewModels()
    lateinit var binding: FragmentAddApplicationBinding

    private val expirationDateCalendar = Calendar.getInstance()
    private val formatter = SimpleDateFormat("dd.MM.yyyy hh:mm", Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddApplicationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backButtonHandler()
        addObjectHandler()

        // Adapters
        initServicesTextView()
        initKindsTextView()
        initPrioritiesAdapter()
        initStatusesAdapter()

        // Date picker handlers
        expirationDateHandler()
    }

    private fun backButtonHandler() {
        binding.fAddApplicationBackButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun getChip(): Chip {
        return Chip(requireContext())
    }

    private fun <T> setupAdapter(items: MutableLiveData<List<T>>, autoCompleteTextView: AutoCompleteTextView) {
        items.observe(viewLifecycleOwner) {
            it.let {
                val adapter = ArrayAdapter(
                    requireContext(),
                    R.layout.item_add_application_dropdown,
                    it
                )

                autoCompleteTextView.setAdapter(adapter)
            }
        }
    }

    private fun initServicesTextView() {
        setupAdapter(viewModel.services, binding.fAddApplicationAutoCompleteService)
        binding.fAddApplicationAutoCompleteService.setOnItemClickListener { _, _, position, _ ->
            binding.fAddApplicationServiceLayout.hint =
                viewModel.services.value?.get(position)?.name
        }
        viewModel.initServices()
    }

    private fun initKindsTextView() {
        setupAdapter(viewModel.kinds, binding.fAddApplicationAutoCompleteKinds)
        binding.fAddApplicationAutoCompleteKinds.setOnItemClickListener { _, _, position, _ ->
            binding.fAddApplicationKindLayout.hint = viewModel.kinds.value?.get(position)?.name
        }
        viewModel.initKinds()
    }

    private fun initPrioritiesAdapter() {
        setupAdapter(viewModel.priorities, binding.fAddApplicationAutoCompletePriority)
        binding.fAddApplicationAutoCompletePriority.setOnItemClickListener { _, _, position, _ ->
            binding.fAddApplicationPriorityLayout.hint =
                viewModel.priorities.value?.get(position)?.name
        }
        viewModel.initPriorities()
    }

    private fun initStatusesAdapter() {
        setupAdapter(viewModel.statuses, binding.fAddApplicationAutoCompleteStatus)
        binding.fAddApplicationAutoCompleteStatus.setOnItemClickListener { _, _, position, _ ->
            binding.fAddApplicationStatusLayout.hint = viewModel.statuses.value?.get(position)?.name
        }
        viewModel.initStatuses()
    }

    private fun addObjectHandler() {
        binding.fAddApplicationAddObjectButton.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())



            binding.fAddApplicationObjectChipGroup.addView(getChip())
        }
    }

    private fun datePickerHandler() {

    }

    private fun planeDateHandler() {
        binding.fAddApplicationPlaneDateText.setOnClickListener {

        }
    }

    private fun expirationDateHandler() {
        binding.fAddApplicationExpirationDateText.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                this,
                expirationDateCalendar.get(Calendar.YEAR),
                expirationDateCalendar.get(Calendar.MONTH),
                expirationDateCalendar.get(Calendar.DAY_OF_MONTH),
            ).show()
        }
    }

    private var year = 0
    private var month = 0
    private var dayOfMonth = 0

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        expirationDateCalendar.set(year, month, dayOfMonth)

        this.year = year
        this.month = month
        this.dayOfMonth = dayOfMonth

        TimePickerDialog(
            requireContext(),
            this,
            0,
            0,
            true
        ).show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        showTime(this.year, this.month, this.dayOfMonth, hourOfDay, minute)
    }

    private fun showTime(year: Int, month: Int, dayOfMonth: Int, hourOfDay: Int, minute: Int) {
        binding.fAddApplicationExpirationDateText.text = "$year.$month.$dayOfMonth $hourOfDay:$minute"
    }
}