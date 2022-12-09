package com.example.sidiay.presentation.fragments.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sidiay.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoadFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_load, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        Handler(Looper.getMainLooper()).postDelayed({
//            findNavController().navigate(R.id.loginFragment)|
//        }, 3000)
        findNavController().navigate(R.id.loginFragment)
    }
}