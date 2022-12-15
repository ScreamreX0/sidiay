package com.example.sidiay.presentation.fragments.menu

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.example.domain.models.params.DateParams
import com.example.sidiay.R
import com.example.sidiay.databinding.FragmentAddApplicationBinding
import com.example.sidiay.presentation.viewmodels.menu.AddApplicationViewModel
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import android.icu.util.TimeZone
import android.view.ContextThemeWrapper
import java.util.*


@AndroidEntryPoint
class AddApplicationFragment : Fragment(R.layout.fragment_add_application),
    DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private val viewModel: AddApplicationViewModel by viewModels()
    lateinit var binding: FragmentAddApplicationBinding


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
        planeDateHandler()
        expirationDateHandler()
        initCreationDate()
    }


    private fun backButtonHandler() {
        binding.fAddApplicationBackButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }


    private fun addObjectHandler() {
        binding.fAddApplicationAddObjectButton.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())

            

            binding.fAddApplicationObjectChipGroup.addView(getChip("Test text"), 0)
        }
    }

    private fun getChip(text: String): Chip {
        val chip = Chip(ContextThemeWrapper(requireContext(), R.style.Widget_Sidiay_Chip_Object), null, 0)

        chip.text = text

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
        binding.fAddApplicationAutoCompleteService.setOnItemClickListener { _, _, position, _ ->

        }
        viewModel.initServices()
    }

    private fun initKindsTextView() {
        setupAdapter(viewModel.kinds, binding.fAddApplicationAutoCompleteKinds)
        binding.fAddApplicationAutoCompleteKinds.setOnItemClickListener { _, _, position, _ ->

        }
        viewModel.initKinds()
    }

    private fun initPrioritiesAdapter() {
        setupAdapter(viewModel.priorities, binding.fAddApplicationAutoCompletePriority)
        binding.fAddApplicationAutoCompletePriority.setOnItemClickListener { _, _, position, _ ->

        }
        viewModel.initPriorities()
    }

    private fun initStatusesAdapter() {
        setupAdapter(viewModel.statuses, binding.fAddApplicationAutoCompleteStatus)
        binding.fAddApplicationAutoCompleteStatus.setOnItemClickListener { _, _, position, _ ->

        }
        viewModel.initStatuses()
    }

    // DateTimes
    private var currentDateTimePicker: TextView? = null
    private val dateParams: DateParams = DateParams(0, 0, 0)

    private val planeDateCalendar = Calendar.getInstance()
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

    private val expirationDateCalendar = Calendar.getInstance()
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

    @SuppressLint("SetTextI18n")
    private fun showTime(year: Int, month: Int, dayOfMonth: Int, hourOfDay: Int, minute: Int) {
        currentDateTimePicker?.text = "$year.$month.$dayOfMonth $hourOfDay:$minute"
    }

    @SuppressLint("SimpleDateFormat")
    private fun initCreationDate() {
        binding.fAddApplicationCreationDateText.text = SimpleDateFormat("yyyy.MM.dd hh:mm", Locale.getDefault())
                .format(Calendar.getInstance(TimeZone.getDefault()).time)
    }
}