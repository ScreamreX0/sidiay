package com.example.sidiay.presentation.fragments.menu

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.icu.util.TimeZone
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.domain.models.params.DateParams
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
    // Common
    private val viewModel: AddApplicationViewModel by viewModels()
    lateinit var binding: FragmentAddApplicationBinding
    private val args by navArgs<AddApplicationFragmentArgs>()

    // Object vars
    private var objects: List<String> = listOf()
    private var selectedObjects = BooleanArray(objects.size)

    // Executor vars
    private var executors: List<String> = listOf()

    // Date vars
    private var currentDateTimePicker: TextView? = null
    private val dateParams: DateParams = DateParams(0, 0, 0)
    private val planeDateCalendar = Calendar.getInstance()
    private val expirationDateCalendar = Calendar.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAddApplicationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAuthor()

        // Init suspend vars
        initObjects()
        initEmployees()

        // Buttons
        backButtonHandler()
        addObjectHandler()
        addExecutorsHandler()

        // Adapters
        initServicesTextView()
        initKindsTextView()
        initPrioritiesAdapter()
        initStatusesAdapter()

        // DatePicker handlers
        planeDateHandler()
        expirationDateHandler()
        initCreationDate()
    }

    private fun backButtonHandler() {
        binding.fAddApplicationBackButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initAuthor() {
        with(args.user) {
            binding.fAddApplicationAuthorText.text = "$firstName $name $lastName"
        }
    }

    // Employees list
    private fun initEmployees() {
        viewModel.initEmployees()

        viewModel.employees.observe(viewLifecycleOwner) {
            viewModel.employees.value?.let { list ->
                executors = list.map { "${it.firstName} ${it.name} ${it.lastName}" }
            }
        }
    }

    private fun addExecutorsHandler() {
        binding.fAddApplicationExecutorText.setOnClickListener {
            if (executors.isEmpty()) {
                return@setOnClickListener
            }

            val builder = AlertDialog.Builder(requireContext())

            builder.setTitle(getString(R.string.select_executor))
                .setItems(executors.toTypedArray()) { _, item ->
                    binding.fAddApplicationExecutorText.text = executors[item]
                }
                .setNegativeButton(getString(R.string.Cancel)) { dialog, _ -> dialog.dismiss() }
                .create()
                .show()
        }
    }

    // Objects list
    private fun initObjects() {
        viewModel.initObjects()

        viewModel.objects.observe(viewLifecycleOwner) {
            viewModel.objects.value?.let { list ->
                objects = list.map { it.name }
                selectedObjects = BooleanArray(objects.size)
            }
        }
    }

    private fun addObjectHandler() {
        binding.fAddApplicationAddObjectButton.setOnClickListener {
            if (objects.isEmpty()) {
                return@setOnClickListener
            }

            val builder = AlertDialog.Builder(requireContext())

            builder.setTitle(getString(R.string.field_object))
                .setItems(objects.toTypedArray()) { dialog, item ->
                    if (!selectedObjects[item]) {
                        selectedObjects[item] = true
                        binding.fAddApplicationObjectChipGroup.addView(getChip(objects[item]), 0)
                    }
                }
                .setNegativeButton(getString(R.string.Cancel)) { dialog, _ -> dialog.dismiss() }
                .create()
                .show()
        }
    }

    private fun getChip(text: String): Chip {
        val chip = Chip(
            ContextThemeWrapper(requireContext(), R.style.Widget_Sidiay_Chip_Object),
            null,
            0
        )

        chip.text = text

        chip.setOnClickListener {
            binding.fAddApplicationObjectChipGroup.removeView(it)
            selectedObjects[objects.indexOf(chip.text)] = false
        }

        return chip
    }

    // Dropdown menu
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
        viewModel.initServices()
    }

    private fun initKindsTextView() {
        setupAdapter(viewModel.kinds, binding.fAddApplicationAutoCompleteKinds)
        viewModel.initKinds()
    }

    private fun initPrioritiesAdapter() {
        setupAdapter(viewModel.priorities, binding.fAddApplicationAutoCompletePriority)
        viewModel.initPriorities()
    }

    private fun initStatusesAdapter() {
        setupAdapter(viewModel.statuses, binding.fAddApplicationAutoCompleteStatus)
        viewModel.initStatuses()
    }

    // DateTimes
    private fun planeDateHandler() {
        binding.fAddApplicationPlaneDateText.setOnClickListener {
            currentDateTimePicker = binding.fAddApplicationPlaneDateText

            DatePickerDialog(
                requireContext(),
                this,
                planeDateCalendar.get(Calendar.YEAR),
                planeDateCalendar.get(Calendar.MONTH),
                planeDateCalendar.get(Calendar.DAY_OF_MONTH),
            ).show()
        }
    }

    private fun expirationDateHandler() {
        binding.fAddApplicationExpirationDateText.setOnClickListener {
            currentDateTimePicker = binding.fAddApplicationExpirationDateText

            DatePickerDialog(
                requireContext(),
                this,
                expirationDateCalendar.get(Calendar.YEAR),
                expirationDateCalendar.get(Calendar.MONTH),
                expirationDateCalendar.get(Calendar.DAY_OF_MONTH),
            ).show()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        expirationDateCalendar.set(year, month, dayOfMonth)

        dateParams.year = year
        dateParams.month = month
        dateParams.day = dayOfMonth

        TimePickerDialog(
            requireContext(),
            this,
            0,
            0,
            true
        ).show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        showTime(dateParams.year, dateParams.month, dateParams.day, hourOfDay, minute)
    }

    private fun showTime(year: Int, month: Int, dayOfMonth: Int, hourOfDay: Int, minute: Int) {
        currentDateTimePicker?.text = "$year.$month.$dayOfMonth $hourOfDay:$minute"
    }

    private fun initCreationDate() {
        binding.fAddApplicationCreationDateText.text =
            SimpleDateFormat("yyyy.MM.dd hh:mm", Locale.getDefault())
                .format(Calendar.getInstance(TimeZone.getDefault()).time)
    }
}