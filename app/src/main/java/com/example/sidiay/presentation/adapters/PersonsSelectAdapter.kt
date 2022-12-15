package com.example.sidiay.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.params.PersonParams
import com.example.sidiay.databinding.ItemSelectPersonBinding

class PersonsSelectAdapter(
    private val persons: List<PersonParams>,
    private val parent: Fragment
) : RecyclerView.Adapter<PersonsSelectAdapter.PersonsSelectViewHolder>() {
    inner class PersonsSelectViewHolder(val binding: ItemSelectPersonBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonsSelectViewHolder {
        val binding = ItemSelectPersonBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return PersonsSelectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PersonsSelectViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = persons.size
}