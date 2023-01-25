package com.example.sidiay.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.enums.ticket.TicketPriorityEnum
import com.example.domain.models.entities.TicketEntity
import com.example.sidiay.databinding.ItemTicketBinding
import com.example.sidiay.presentation.fragments.menu.TicketsListFragmentDirections

class TicketsListAdapter(
    private val ticketEntities: List<TicketEntity>,
    private val parent: Fragment
) : RecyclerView.Adapter<TicketsListAdapter.TicketViewHolder>() {
    inner class TicketViewHolder(val binding: ItemTicketBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        val binding = ItemTicketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TicketViewHolder(binding)
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        with(holder) {
            with(ticketEntities[position]) {
                service?.let { binding.iTicketCompany.text = it }
                priority?.let { binding.iTicketPriority.text = TicketPriorityEnum.valueOf(it.toInt()).getName() }
                executor?.let { binding.iTicketPerson.text = "${it.firstname} ${it.name.first()}. ${it.lastname.first()}." }
                expiration_date?.let { binding.iTicketDate.text = it }
                status?.let { binding.iTicketStatus.text = it }
                id?.let {itId ->
                    name?.let { itName ->
                        binding.iTicketTitle.text = "$itId№ $itName"
                    }
                }

            }
        }

        holder.itemView.setOnClickListener {
            parent.findNavController().navigate(TicketsListFragmentDirections.actionFragmentTicketsToTicketItemFragment(ticketEntities[position]))
        }
    }

    override fun getItemCount() = ticketEntities.size
}