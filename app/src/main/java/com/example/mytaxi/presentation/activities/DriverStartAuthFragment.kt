package com.example.mytaxi.presentation.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.mytaxi.R
import com.example.mytaxi.databinding.FragmentDriverStartAuthBinding

class DriverStartAuthFragment : Fragment() {


    private var _startAuthBinding: FragmentDriverStartAuthBinding? = null
    private val startAuthBinding get() = _startAuthBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _startAuthBinding = FragmentDriverStartAuthBinding.inflate(inflater, container, false)
        return startAuthBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startAuthBinding.btnContinueToPartnership.setOnClickListener {
            view.findNavController()
                .navigate(R.id.action_driverStartAuthFragment_to_driverAuthPartnershipFragment)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _startAuthBinding = null
    }
}