package com.example.sidiay.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.entities.Ticket
import com.example.sidiay.databinding.ItemTicketBinding
import com.example.sidiay.presentation.fragments.menu.TicketsFragmentDirections

class TicketsAdapter(
    private val tickets: List<Ticket>,
    private val parent: Fragment
) : RecyclerView.Adapter<TicketsAdapter.TicketViewHolder>() {
    inner class TicketViewHolder(val binding: ItemTicketBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        val binding = ItemTicketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TicketViewHolder(binding)
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        with(holder) {
            with(tickets[position]) {
                binding.iTicketCompany.text = service
                binding.iTicketPriority.text = priority
                executor?.let {
                    binding.iTicketPerson.text =
                        "${it.firstName} ${it.name.first()}. ${it.lastName.first()}."
                }
                binding.iTicketDate.text = expirationDate
                binding.iTicketStatus.text = status
                binding.iTicketTitle.text = "$id№ $description"
            }
        }

        holder.itemView.setOnClickListener {
            val action =
                TicketsFragmentDirections.actionFragmentTicketsToTicketItemFragment(
                    tickets[position]
                )
            parent.findNavController().navigate(action)
        }
    }

    override fun getItemCount() = tickets.size
}