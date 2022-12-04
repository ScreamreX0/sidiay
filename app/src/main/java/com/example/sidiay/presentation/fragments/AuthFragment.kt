package com.example.sidiay.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.sidiay.databinding.FragmentAuthBinding
import com.example.sidiay.presentation.viewmodels.SignInFragmentViewModel
import com.example.domain.enums.SignInStatuses
import com.example.sidiay.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthFragment : Fragment(R.layout.fragment_auth) {
    private val viewModel: SignInFragmentViewModel by viewModels()
    private lateinit var binding: FragmentAuthBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // BUTTONS
        enterButtonHandler()        // Auth button
        forgotPasswordHandler()     // Forgot button

        // OBSERVERS
        errorsHandler()             // Errors observing
        successHandler()            // Success observing
    }

    private fun enterButtonHandler() {
        binding.fAuthButton.setOnClickListener {
            viewModel.signIn(
                binding.fAuthEmail.text.toString(),
                binding.fAuthPassword.text.toString()
            )
        }
    }

    private fun errorsHandler() {
        viewModel.errorResult.observe(viewLifecycleOwner) {
            // Connection
            if (SignInStatuses.NO_SERVER_CONNECTION in viewModel.errorResult.value!!) {
                Toast.makeText(context, getString(R.string.no_server_connection), Toast.LENGTH_SHORT).show()
                return@observe
            }

            // Email length
            if (SignInStatuses.SHORT_OR_LONG_EMAIL in viewModel.errorResult.value!!) {
                binding.fAuthEmailContainer.error = getString(R.string.short_or_long_email_briefly)
                binding.fAuthEmailContainer.isErrorEnabled = true
            } else {
                binding.fAuthEmailContainer.isErrorEnabled = false
            }

            // Password length
            if (SignInStatuses.SHORT_OR_LONG_PASSWORD in viewModel.errorResult.value!!) {
                binding.fAuthPasswordContainer.error = getString(R.string.short_or_long_password_briefly)
                binding.fAuthPasswordContainer.isErrorEnabled = true
            } else {
                binding.fAuthPasswordContainer.isErrorEnabled = false
            }

            // Wrong email or password
            if (viewModel.errorResult.value!!.contains(SignInStatuses.WRONG_EMAIL_OR_PASSWORD)) {
                Toast.makeText(context, getString(R.string.wrong_email_or_password), Toast.LENGTH_SHORT).show()
                return@observe
            }
        }
    }

    private fun successHandler() {
        viewModel.successResult.observe(viewLifecycleOwner) {
            val action = AuthFragmentDirections.actionLoginFragmentToMainMenuFragment(viewModel.successResult.value!!)
            findNavController().navigate(action)
        }
    }

    private fun forgotPasswordHandler() {
        binding.fAuthForgotPassword.setOnClickListener {
            // TODO: forgot password
        }
    }
}