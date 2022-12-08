package com.example.sidiay.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.data.models.Application
import com.example.sidiay.databinding.ItemApplicaitonBinding
import java.text.SimpleDateFormat

class ApplicationsAdapter(private val applications: List<Application>) : RecyclerView.Adapter<ApplicationsAdapter.ApplicationViewHolder>() {
    inner class ApplicationViewHolder(val binding: ItemApplicaitonBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApplicationViewHolder {
        val binding = ItemApplicaitonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ApplicationViewHolder(binding)
    }

    override fun getItemCount() = applications.size

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onBindViewHolder(holder: ApplicationViewHolder, position: Int) {
        with(holder) {
            with(applications[position]) {
                binding.iApplicationCompany.text = service
                binding.iApplicationPriority.text = priority
                executor.let {
                    "${it!!.firstName} ${executor!!.name.first()}. ${executor!!.lastName.first()}."
                }
                expirationDate.let {
                    binding.iApplicationDate.text = SimpleDateFormat("dd-MM-yyyy").format(it)
                }
                binding.iApplicationStatus.text = status
            }
        }
    }
}