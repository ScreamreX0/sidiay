//package com.example.home
//
//import android.annotation.SuppressLint
//import android.app.AlertDialog
//import android.app.DatePickerDialog
//import android.app.TimePickerDialog
//import android.icu.util.Calendar
//import android.icu.util.TimeZone
//import android.os.Bundle
//import android.view.ContextThemeWrapper
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.*
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.viewModels
//import androidx.lifecycle.MutableLiveData
//import androidx.navigation.fragment.findNavController
//import androidx.navigation.fragment.navArgs
//import com.example.domain.enums.states.TicketCreateStates
//import com.example.domain.enums.ticket.ITicketEnum
//import com.example.domain.models.entities.KindEntity
//import com.example.domain.models.entities.UserEntity
//import com.example.domain.models.params.AddTicketParams
//import com.example.domain.models.params.DateParams
//import com.example.home.databinding.FragmentTicketCreateBinding
//import com.example.home.ui.ticket_create.TicketCreateViewModel
//import com.google.android.material.chip.Chip
//import dagger.hilt.android.AndroidEntryPoint
//import java.text.SimpleDateFormat
//import java.util.*
//import com.example.core.R as coreR
//
//@AndroidEntryPoint
//class TicketCreateFragment : Fragment(R.layout.fragment_ticket_create), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
//    private val viewModel: TicketCreateViewModel by viewModels()
//    private lateinit var binding: FragmentTicketCreateBinding
//    private val args by navArgs<TicketCreateFragmentArgs>()
//
//    // Selected vars
//    private var selectedKind: KindEntity? = null
//    private var selectedPriority: Long = 1
//    private var selectedService: String = ""
//    private var selectedStatus: String = ""
//
//    // Facility
//    private var facilities: List<String> = listOf()
//    private var selectedFacilities = BooleanArray(facilities.size)
//
//    // Executor
//    private var executors: List<String> = listOf()
//    private var selectedExecutor: UserEntity = UserEntity()
//
//    // Date
//    private var currentDateTimePicker: TextView? = null
//    private val dateParams: DateParams = DateParams(0, 0, 0)
//    private val planeDateCalendar = Calendar.getInstance()
//    private val expirationDateCalendar = Calendar.getInstance()
//
//    // Main ticket
//    private val ticket = AddTicketParams()
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
//        binding = FragmentTicketCreateBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        // Init suspend vars
//        initFacilitiesChipGroup()
//        initEmployees()
//        initAuthor()
//        initFieldsCheckResult()
//
//        // Buttons
//        onClickBackButton()
//        onClickFacilityAdd()
//        onClickExecutorAdd()
//        onClickSaveTicket()
//
//        // Adapters
//        initServicesTextView()
//        initKindsTextView()
//        initPrioritiesAdapter()
//        initStatusesAdapter()
//
//        // Date
//        onClickPlaneDate()
//        onClickExpirationDate()
//        initCreationDate()
//    }
//
//    @SuppressLint("SetTextI18n")
//    private fun initAuthor() {
//        with(args.user) {
//            binding.fAddTicketAuthorText.text = "$firstname $name $lastname"
//        }
//    }
//
//    // Buttons handlers
//    private fun onClickBackButton() {
//        binding.fAddTicketBackButton.setOnClickListener {
//            findNavController().popBackStack()
//        }
//    }
//
//    private fun onClickSaveTicket() {
//        binding.fAddTicketSaveButton.setOnClickListener {
//            /*            // Set facilities
//            viewModel.facilities.value?.let {
//                val facilitiesArrayList: ArrayList<FacilityEntity> = arrayListOf()
//                for (i in selectedFacilities.indices) {
//                    if (selectedFacilities[i]) {
//                        facilitiesArrayList.add(viewModel.facilities.value!![i])
//                    }
//                }
//                ticket.facilities = facilitiesArrayList
//            }
//
//
//
//            // Set author
//            ticket.author = args.user
//
//            // Set executor
//            ticket.executor = selectedExecutor*/
//
//            // Set priority
//            ticket.priority = selectedPriority
//
//            // Set kind
//            ticket.kind = selectedKind
//
//            // Set status
//            ticket.status = selectedStatus
//
//            // Set service
//            ticket.service = selectedService
//
//            // Set plane date
//            if (!binding.fAddTicketPlaneDateText.text.equals(getString(coreR.string.select_date))) {
//                ticket.plane_date = binding.fAddTicketPlaneDateText.text.toString()
//            }
//
//            // Set expiration date
//            if (!binding.fAddTicketExpirationDateText.text.equals(getString(coreR.string.select_date))) {
//                ticket.expiration_date = binding.fAddTicketExpirationDateText.text.toString()
//            }
//
//            // Set creation date
//            ticket.creation_date = binding.fAddTicketCreationDateText.text.toString()
//
//            // Set completed work
//            ticket.completed_work = binding.fAddTicketCompletedWorkEdit.text.toString()
//
//            // Set description
//            ticket.description = binding.fAddTicketDescriptionEdit.text.toString()
//
//            // Set name
//            ticket.name = binding.fAddTicketSecondTitleEdit.text.toString()
//
//            viewModel.checkTicketFields(ticket)
//        }
//    }
//
//    private fun initFieldsCheckResult() {
//        viewModel.mutableFieldsCheckResult.observe(viewLifecycleOwner) {
//            it?.let {
//                if (it.contains(TicketCreateStates.FILL_ALL_FIELDS_WITH_STAR)) {
//                    Toast.makeText(requireContext(), getText(coreR.string.fill_all_fields_with_star), Toast.LENGTH_SHORT).show()
//                    return@observe
//                }
//
//                if (it.contains(TicketCreateStates.READY_TO_BE_SAVED)) {
//                    viewModel.save(ticket)
//                    Toast.makeText(requireContext(), getString(coreR.string.ticket_create_success), Toast.LENGTH_SHORT).show()
//                    findNavController().popBackStack()
//                }
//            }
//        }
//    }
//
//    // USERS
//    // Employees
//    private fun initEmployees() {
//        viewModel.initEmployees()
//
//        viewModel.mutableEmployees.observe(viewLifecycleOwner) {
//            viewModel.mutableEmployees.value?.let { list ->
//                executors = list.map { "${it.firstname} ${it.name} ${it.lastname}" }
//            }
//        }
//    }
//
//    //
//    // Executors
//    private fun onClickExecutorAdd() {
//        binding.fAddTicketExecutorText.setOnClickListener {
//            if (executors.isEmpty()) {
//                return@setOnClickListener
//            }
//
//            AlertDialog.Builder(requireContext()).setTitle(getString(coreR.string.select_executor))
//                .setItems(executors.toTypedArray()) { _, item ->
//                    binding.fAddTicketExecutorText.text = executors[item]
//                    viewModel.mutableEmployees.value?.let {
//                        selectedExecutor = it[item]
//                    }
//                }
//                .setNegativeButton(getString(coreR.string.cancel)) { dialog, _ -> dialog.dismiss() }
//                .create()
//                .show()
//        }
//    }
//
//    //
//    // Facilities list
//    private fun initFacilitiesChipGroup() {
//        viewModel.initFacilities()
//
//        viewModel.mutableFacilities.observe(viewLifecycleOwner) {
//            viewModel.mutableFacilities.value?.let { list ->
//                facilities = list.map { it.name }
//                selectedFacilities = BooleanArray(facilities.size)
//            }
//        }
//    }
//
//    private fun onClickFacilityAdd() {
//        binding.fAddTicketAddObjectButton.setOnClickListener {
//            if (facilities.isEmpty()) {
//                return@setOnClickListener
//            }
//
//            AlertDialog.Builder(requireContext())
//                .setTitle(getString(coreR.string.field_object))
//                .setItems(facilities.toTypedArray()) { _, item ->
//                    if (!selectedFacilities[item]) {
//                        selectedFacilities[item] = true
//                        binding.fAddTicketObjectChipGroup.addView(getChip(facilities[item]), 0)
//                    }
//                }
//                .setNegativeButton(getString(coreR.string.cancel)) { dialog, _ -> dialog.dismiss() }
//                .create()
//                .show()
//        }
//    }
//
//    private fun getChip(text: String): Chip {
//        val chip = Chip(ContextThemeWrapper(requireContext(), coreR.style.Widget_Sidiay_Chip_Object), null, 0)
//
//        chip.text = text
//
//        chip.setOnClickListener {
//            binding.fAddTicketObjectChipGroup.removeView(it)
//            selectedFacilities[facilities.indexOf(chip.text)] = false
//        }
//
//        return chip
//    }
//
//    //
//    // DROPDOWN
//    private fun <T> setupAutoCompleteTextViewAdapter(mutableItems: MutableLiveData<List<T>>, autoCompleteTextView: AutoCompleteTextView) {
//        mutableItems.observe(viewLifecycleOwner) { itListNullable ->
//            itListNullable?.let { itList ->
//                val elementsList = itList.map { (it as ITicketEnum).getName() }.toList()
//                autoCompleteTextView.setAdapter(ArrayAdapter(requireContext(),
//                    R.layout.item_ticket_create_dropdown, elementsList))
//            }
//        }
//    }
//    private fun initServicesTextView() {
//        setupAutoCompleteTextViewAdapter(viewModel.mutableServices, binding.fAddTicketAutoCompleteService)
//
//        viewModel.initServices()
//
//        binding.fAddTicketAutoCompleteService.setOnItemClickListener { _, _, position, _ ->
//            viewModel.mutableServices.value?.let {
//                selectedService = it[position].getName()
//            }
//        }
//    }
//
//    private fun initKindsTextView() {
//        setupAutoCompleteTextViewAdapter(viewModel.mutableKinds, binding.fAddTicketAutoCompleteKinds)
//        viewModel.initKinds()
//
//        binding.fAddTicketAutoCompleteKinds.setOnItemClickListener { _, _, position, _ ->
//            viewModel.mutableKinds.value?.let {
//                selectedKind = KindEntity(it[position].id, it[position].getName())
//            }
//        }
//    }
//
//    private fun initPrioritiesAdapter() {
//        setupAutoCompleteTextViewAdapter(viewModel.mutablePriorities, binding.fAddTicketAutoCompletePriority)
//        viewModel.initPriorities()
//
//        binding.fAddTicketAutoCompletePriority.setOnItemClickListener { _, _, position, _ ->
//            viewModel.mutablePriorities.value?.let {
//                selectedPriority = it[position].priority.toLong()
//            }
//        }
//    }
//
//    private fun initStatusesAdapter() {
//        setupAutoCompleteTextViewAdapter(viewModel.mutableStatuses, binding.fAddTicketAutoCompleteStatus)
//        viewModel.initStatuses()
//
//        binding.fAddTicketAutoCompleteStatus.setOnItemClickListener { _, _, position, _ ->
//            viewModel.mutableStatuses.value?.let {
//                selectedStatus = it[position].getName()
//            }
//        }
//    }
//
//    // DATE
//    private fun onClickPlaneDate() {
//        binding.fAddTicketPlaneDateText.setOnClickListener {
//            currentDateTimePicker = binding.fAddTicketPlaneDateText
//
//            DatePickerDialog(
//                requireContext(),
//                this,
//                planeDateCalendar.get(Calendar.YEAR),
//                planeDateCalendar.get(Calendar.MONTH),
//                planeDateCalendar.get(Calendar.DAY_OF_MONTH),
//            ).show()
//        }
//    }
//
//    private fun onClickExpirationDate() {
//        binding.fAddTicketExpirationDateText.setOnClickListener {
//            currentDateTimePicker = binding.fAddTicketExpirationDateText
//
//            DatePickerDialog(
//                requireContext(),
//                this,
//                expirationDateCalendar.get(Calendar.YEAR),
//                expirationDateCalendar.get(Calendar.MONTH),
//                expirationDateCalendar.get(Calendar.DAY_OF_MONTH),
//            ).show()
//        }
//    }
//
//    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
//        expirationDateCalendar.set(year, month, dayOfMonth)
//
//        dateParams.year = year
//        dateParams.month = month + 1
//        dateParams.day = dayOfMonth
//
//        TimePickerDialog(
//            requireContext(),
//            this,
//            0,
//            0,
//            true
//        ).show()
//    }
//
//    @SuppressLint("SetTextI18n")
//    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
//        currentDateTimePicker?.text = "" +
//                "${fillDateWithZeros(dateParams.day)}." +
//                "${fillDateWithZeros(dateParams.month)}." +
//                "${dateParams.year} " +
//                "${fillDateWithZeros(hourOfDay)}:" +
//                "${fillDateWithZeros(minute)}"
//    }
//
//    private fun fillDateWithZeros(value: Int): String {
//        if (value < 10) {
//            return "0${value}"
//        }
//        return value.toString()
//    }
//
//    private fun initCreationDate() {
//        binding.fAddTicketCreationDateText.text = SimpleDateFormat("dd.MM.yyyy hh:mm", Locale.getDefault())
//            .format(Calendar.getInstance(TimeZone.getDefault()).time)
//    }
//}