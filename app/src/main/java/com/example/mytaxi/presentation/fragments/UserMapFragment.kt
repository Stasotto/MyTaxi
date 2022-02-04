package com.example.mytaxi.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mytaxi.R
import com.example.mytaxi.databinding.FragmentMapUserBinding

class UserMapFragment : Fragment(R.layout.fragment_map_user) {

    private val binding by viewBinding(FragmentMapUserBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            onGoClickListener()
        onDelivery()
    }

    private fun onDelivery() {
        binding.delivery.setOnClickListener {

        }
    }

    private fun onGoClickListener() {
        binding.go.setOnClickListener {
            initDialog()

        }
    }
    private fun initDialog() {
        val dialog = StartMapDialogFrag.newInstance()
        dialog.show(requireActivity().supportFragmentManager, StartMapDialogFrag.TAG)
    }

    companion object{
        const val TAG = "User Map "

        @JvmStatic
        fun newInstance() = UserMapFragment()
    }
}