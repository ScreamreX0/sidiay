package com.example.sidiay.presentation.fragments.menu

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.data.models.Application
import com.example.data.models.Employee
import com.example.sidiay.R
import com.example.sidiay.databinding.FragmentApplicationsBinding
import com.example.sidiay.presentation.adapters.ApplicationsAdapter
import com.example.sidiay.presentation.viewmodels.menu.ApplicationFragmentViewModel
import java.text.SimpleDateFormat
import java.util.*
import com.example.data.models.Object
import java.text.DateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.logging.Level.parse

class ApplicationFragment : Fragment(R.layout.fragment_applications) {
    private val viewModel: ApplicationFragmentViewModel by viewModels()
    private lateinit var binding: FragmentApplicationsBinding

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
    }

    @SuppressLint("SimpleDateFormat", "NewApi")
    fun initRecyclerView() {
        val applications = List(10) {
            Application(
                id = it,
                service = "$it Ихсанов",
                description = "Покрасить подставку под КТП, ТД, ТП, ТО",
                priority = "Средний",
                status = "Не закрыта",
                executor = Employee(id = it * 2, firstName = "$it", name = "${it+1}", lastName = "${it+2}"),
                plannedDate = "05-03-2003",
                expirationDate = "05-03-2003",
                author = Employee(id = it * 3, firstName = "$it", name = "${it+1}", lastName = "${it+2}"),
                creationDate = "05-03-2003",
                objects = listOf(Object(id = 1), Object(id = 2), Object(id = 3)),
            )
        }

        val layoutManager = LinearLayoutManager(context)
        binding.fApplicationRecyclerView.layoutManager = layoutManager
        binding.fApplicationRecyclerView.setHasFixedSize(true)

        binding.fApplicationRecyclerView.adapter = ApplicationsAdapter(applications)
    }
}