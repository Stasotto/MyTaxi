package com.example.mytaxi.presentation.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.mytaxi.R
import com.example.mytaxi.databinding.FragmentDriverAuthPartnershipBinding

class DriverAuthPartnershipFragment : Fragment() {


    private var _partnershipAuthBinding: FragmentDriverAuthPartnershipBinding? = null
    private val partnershipAuthBinding get() = _partnershipAuthBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _partnershipAuthBinding = FragmentDriverAuthPartnershipBinding.inflate(inflater, container, false)
        return partnershipAuthBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        partnershipAuthBinding.btnContinueToAddPhotos.setOnClickListener {
            view.findNavController()
                .navigate(R.id.action_driverAuthPartnershipFragment_to_driverAddPhotosFragment)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _partnershipAuthBinding = null
    }
}