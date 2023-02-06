package com.example.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.domain.enums.ticket.TicketPriorityEnum
import com.example.home.databinding.FragmentTicketItemBinding
import dagger.hilt.android.AndroidEntryPoint
import com.example.core.R as coreR

@AndroidEntryPoint
class TicketItemFragment : Fragment(R.layout.fragment_ticket_item) {
    private val viewModel: TicketItemViewModel by viewModels()
    private lateinit var binding: FragmentTicketItemBinding
    // private val args by navArgs<TicketItemFragmentArgs>() TODO

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

//        with(args.ticket) {TODO
//            with(binding) {
//                id?.let { fTicketItemTitle.text = "${getString(coreR.string.ticket)}№ $it" }
//                name?.let { fTicketItemSecondTitle.text = it }
//                service?.let { fTicketItemServiceText.text = it }
//                completed_work?.let { fTicketItemCompletedWorkText.text = it }
//                creation_date?.let { fTicketItemCreationDateText.text = it }
//                expiration_date?.let { fTicketItemExpirationDateText.text = it }
//                plane_date?.let { fTicketItemPlaneDateText.text = it }
//
//                facilities?.let { itemFacilityEntity ->
//                    fTicketItemObjectText.text = itemFacilityEntity.joinToString(
//                        separator = "/",
//                        transform = { it.name }
//                    )
//                }
//
//                priority?.let { fTicketItemPriorityText.text = TicketPriorityEnum.valueOf(it.toInt()).getName() }
//                status?.let { fTicketItemStatusText.text = it }
//                kind?.name?.let { fTicketItemKindText.text = it }
//                description?.let { fTicketItemDescriptionText.text = it}
//
//                author?.let {
//                    fTicketItemAuthorText.text = "${it.firstname} ${it.name.first()}. ${it.lastname.first()}."
//                }
//
//                executor?.let {
//                    fTicketItemExecutorText.text = "${it.firstname} ${it.name.first()}. ${it.lastname.first()}."
//                }
//            }
//        }
    }

    private fun onBackButtonClick() {
        binding.fTicketItemBackButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}