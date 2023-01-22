package com.example.sidiay.presentation.fragments.menu

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.sidiay.R
import com.example.sidiay.databinding.FragmentTicketItemBinding
import com.example.sidiay.presentation.viewmodels.menu.TicketItemViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TicketItemFragment : Fragment(R.layout.fragment_ticket_item) {
    private val viewModel: TicketItemViewModel by viewModels()
    private lateinit var binding: FragmentTicketItemBinding
    private val args by navArgs<TicketItemFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTicketItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onBackButtonClick()

        with(args.ticket) {
            with(binding) {
                fTicketItemTitle.text = "${getString(R.string.ticket)}№ $id"
                fTicketItemSecondTitle.text = title

                fTicketItemServiceText.text = service

                fTicketItemCompletedWorkText.text = completedWorks

                fTicketItemCreationDateText.text = creationDate
                fTicketItemExpirationDateText.text = expirationDate
                fTicketItemPlaneDateText.text = plannedDate

                fTicketItemObjectText.text = objects?.joinToString(
                    separator = "/",
                    transform = { it.name }
                )

                fTicketItemPriorityText.text = priority
                fTicketItemStatusText.text = status
                fTicketItemKindText.text = type

                fTicketItemDescriptionText.text = description

                // Author full name
                fTicketItemAuthorText.text = author?.let {
                    "${author!!.firstName} ${author!!.name.first()}. ${author!!.lastName.first()}."
                }

                // Executor full name
                fTicketItemExecutorText.text = executor?.let {
                    "${executor!!.firstName} ${executor!!.name.first()}. ${executor!!.lastName.first()}."
                }
            }
        }
    }

    private fun onBackButtonClick() {
        binding.fTicketItemBackButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}