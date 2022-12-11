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
import com.example.sidiay.databinding.FragmentApplicationItemBinding
import com.example.sidiay.presentation.viewmodels.menu.ApplicationItemViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ApplicationItemFragment : Fragment(R.layout.fragment_application_item) {
    private val viewModel: ApplicationItemViewModel by viewModels()
    private lateinit var binding: FragmentApplicationItemBinding
    private val args by navArgs<ApplicationItemFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentApplicationItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(args.application) {
            with(binding) {
                fApplicationItemTitle.text = "${getString(R.string.application)}№ $id"
                fApplicationItemTitleSecond.text = title

                fApplicationItemService.text = service

                fApplicationItemCompletedWork.text = completedWorks

                fApplicationItemDateOfCreation.text = creationDate
                fApplicationItemExpirationDate.text = expirationDate
                fApplicationItemPlaneDate.text = plannedDate

                fApplicationItemObject.text = objects?.joinToString(
                    separator = "/",
                    transform = { it.name }
                )

                fApplicationItemPriority.text = priority
                fApplicationItemStatus.text = status
                fApplicationItemKind.text = type

                fApplicationItemDescription.text = description

                // Author full name
                fApplicationItemAuthor.text = author?.let {
                    "${author!!.firstName} ${author!!.name.first()}. ${author!!.lastName.first()}."
                }

                // Executor full name
                fApplicationItemExecutor.text = executor?.let {
                    "${executor!!.firstName} ${executor!!.name.first()}. ${executor!!.lastName.first()}."
                }
            }
        }

        binding.fApplicationItemBackButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}