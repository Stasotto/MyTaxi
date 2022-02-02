package com.example.mytaxi.presentation.activities

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.mytaxi.R
import com.example.mytaxi.databinding.FragmentDriverAddPhotosBinding
import com.example.mytaxi.databinding.FragmentDriverAuthPartnershipBinding


class DriverAddPhotosFragment : Fragment() {


    private var _addPhotosBinding: FragmentDriverAddPhotosBinding? = null
    private val addPhotosBinding get() = _addPhotosBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _addPhotosBinding = FragmentDriverAddPhotosBinding.inflate(inflater, container, false)
        return addPhotosBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addPhotosBinding.btnDoneReg.setOnClickListener {
            view.findNavController()
                .navigate(R.id.action_driverAddPhotosFragment_to_driverAuthMakePhotoFragment)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _addPhotosBinding = null
    }
}