package com.example.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.domain.enums.states.SignInStates
import com.example.signin.databinding.FragmentSigninBinding
import dagger.hilt.android.AndroidEntryPoint
import com.example.core.R as coreR

@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_signin) {
    private val viewModel: SignInViewModel by viewModels()
    private lateinit var binding: FragmentSigninBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setContent {
                //SignInScreen()
            }
        }
        binding = FragmentSigninBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        // BUTTONS
//        enterButtonHandler()
//
//        // OBSERVERS
//        errorsHandler()
//        successHandler()
    }

    private fun enterButtonHandler() {
        binding.fSignInButton.setOnClickListener {
            binding.fSignInButton.isClickable = false
            viewModel.signIn(
                binding.fSignInEmail.text.toString(),
                binding.fSignInPassword.text.toString()
            )
        }
    }

    private fun errorsHandler() {
        viewModel.errorResult.observe(viewLifecycleOwner) {
            binding.fSignInButton.isClickable = true

            // Connection
            if (SignInStates.NO_SERVER_CONNECTION in viewModel.errorResult.value!!) {
                Toast.makeText(
                    context,
                    getString(coreR.string.no_server_connection),
                    Toast.LENGTH_SHORT
                ).show()
                return@observe
            }

            // Email length
            if (SignInStates.SHORT_OR_LONG_EMAIL in viewModel.errorResult.value!!) {
                binding.fAuthEmailContainer.error =
                    getString(coreR.string.short_or_long_email_briefly)
                binding.fAuthEmailContainer.isErrorEnabled = true
            } else {
                binding.fAuthEmailContainer.isErrorEnabled = false
            }

            // Password length
            if (SignInStates.SHORT_OR_LONG_PASSWORD in viewModel.errorResult.value!!) {
                binding.fAuthPasswordContainer.error =
                    getString(coreR.string.short_or_long_password_briefly)
                binding.fAuthPasswordContainer.isErrorEnabled = true
            } else {
                binding.fAuthPasswordContainer.isErrorEnabled = false
            }

            // Wrong email or password
            if (viewModel.errorResult.value!!.contains(SignInStates.WRONG_EMAIL_OR_PASSWORD)) {
                Toast.makeText(
                    context,
                    getString(coreR.string.wrong_email_or_password),
                    Toast.LENGTH_SHORT
                ).show()
                return@observe
            }
        }
    }

    private fun successHandler() {
        viewModel.successSignIn.observe(viewLifecycleOwner) {
            val bundle = Bundle()
            bundle.putParcelable("user", viewModel.successSignIn.value!!)
            findNavController().setGraph(
                com.example.main_menu.R.navigation.main_menu_graph,
                bundle
            )
        }
    }
}

