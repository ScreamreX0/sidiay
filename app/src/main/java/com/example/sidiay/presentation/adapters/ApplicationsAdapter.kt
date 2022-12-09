package com.example.sidiay.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.entities.Application
import com.example.sidiay.databinding.ItemApplicaitonBinding
import com.example.sidiay.presentation.fragments.menu.ApplicationsFragmentDirections

class ApplicationsAdapter(
    private val applications: List<Application>,
    private val parent: Fragment
) : RecyclerView.Adapter<ApplicationsAdapter.ApplicationViewHolder>() {
    inner class ApplicationViewHolder(val binding: ItemApplicaitonBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApplicationViewHolder {
        val binding =
            ItemApplicaitonBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ApplicationViewHolder(binding)
    }

    override fun getItemCount() = applications.size

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onBindViewHolder(holder: ApplicationViewHolder, position: Int) {
        with(holder) {
            with(applications[position]) {
                binding.iApplicationCompany.text = service
                binding.iApplicationPriority.text = priority
                executor?.let {
                    binding.iApplicationPerson.text =
                        "${it.firstName} ${it.name.first()}. ${it.lastName.first()}."
                }
                binding.iApplicationDate.text = expirationDate
                binding.iApplicationStatus.text = status
                binding.iApplicationTitle.text = "$id№ $description"
            }
        }

        holder.itemView.setOnClickListener {
            val action = ApplicationsFragmentDirections.actionFragmentApplicationsToApplicationItemFragment(
                applications[position]
            )
            parent.findNavController().navigate(action)
        }
    }
}