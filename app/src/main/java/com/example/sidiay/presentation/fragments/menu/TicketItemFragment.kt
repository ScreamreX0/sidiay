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
import com.example.domain.enums.PriorityState
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
                name?.let { fTicketItemSecondTitle.text = it }
                service?.let { fTicketItemServiceText.text = it }

                completed_work?.let { fTicketItemCompletedWorkText.text = it }
                creation_date?.let { fTicketItemCreationDateText.text = it }
                expiration_date?.let { fTicketItemExpirationDateText.text = it }
                plane_date?.let { fTicketItemPlaneDateText.text = it }

                facilities?.let {
                    fTicketItemObjectText.text = it.joinToString(
                        separator = "/",
                        transform = { it.name }
                    )
                }

                priority?.let { fTicketItemPriorityText.text = PriorityState.valueOf(it).title }
                status?.let { fTicketItemStatusText.text = it }
                kindEntity?.name.let { fTicketItemKindText.text = it }

                description?.let { fTicketItemDescriptionText.text = it }

                author?.let {
                    fTicketItemAuthorText.text = "${it.firstname} ${it.name.first()}. ${it.lastname.first()}."
                }

                executor?.let {
                    fTicketItemExecutorText.text = "${it.firstname} ${it.name.first()}. ${it.lastname.first()}."
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