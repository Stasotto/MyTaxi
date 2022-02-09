package com.example.mytaxi.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mytaxi.R
import com.example.mytaxi.databinding.DeliveryBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DeliveryFragment : BottomSheetDialogFragment() {
    companion object {
        const val TAG = "Delivery"

        fun newInstance() = DeliveryFragment()

    }

    private val binding: DeliveryBinding by viewBinding(DeliveryBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.delivery, container, false)
    }
}