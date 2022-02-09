package com.example.mytaxi.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.mytaxi.R
import com.example.mytaxi.databinding.FragmentDriverAuthPartnershipBinding
import com.example.mytaxi.presentation.viewmodels.DriverViewModel

class DriverAuthPartnershipFragment : Fragment() {


    private var _partnershipAuthBinding: FragmentDriverAuthPartnershipBinding? = null
    private val partnershipAuthBinding get() = _partnershipAuthBinding!!
    private val driverViewModel: DriverViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _partnershipAuthBinding =
            FragmentDriverAuthPartnershipBinding.inflate(inflater, container, false)
        return partnershipAuthBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        partnershipAuthBinding.rbLivery.setOnClickListener {
            partnershipAuthBinding.rbLight.isChecked = false
            partnershipAuthBinding.rbLivery.isChecked = true
            driverViewModel.driverData.value?.partnershipVariant = 1
        }

        partnershipAuthBinding.rbLight.setOnClickListener {
            partnershipAuthBinding.rbLight.isChecked = true
            partnershipAuthBinding.rbLivery.isChecked = false
            driverViewModel.driverData.value?.partnershipVariant = 2
        }

        partnershipAuthBinding.btnContinueToAddPhotos.setOnClickListener {
            if (driverViewModel.driverData.value!!.partnershipVariant == 0) {
                Toast.makeText(
                    requireActivity().applicationContext,
                    R.string.toastPartnership,
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                view.findNavController()
                    .navigate(R.id.action_driverAuthPartnershipFragment_to_driverAddPhotosFragment)
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _partnershipAuthBinding = null
    }


}