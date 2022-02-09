package com.example.mytaxi.presentation.fragments

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mytaxi.R
import com.example.mytaxi.databinding.FragmentMapUserBinding
import com.example.mytaxi.presentation.viewmodels.UserMapViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class UserMapFragment : Fragment(R.layout.fragment_map_user) {

    private var _binding: FragmentMapUserBinding? = null
    private val binding get() = _binding!!
    private val handler by lazy { Handler() }

    private val runnable: Runnable = object : Runnable {
        override fun run() {
            if (UserMapViewModels.a != null) {
                val disText =
                    "Расстояние: ${UserMapViewModels.a?.rows?.get(0)?.elements?.get(0)?.distance?.text}"
                val durText =
                    "Время в пути: ${UserMapViewModels.a?.rows?.get(0)?.elements?.get(0)?.duration?.text}"
                binding.distance.text = disText
                binding.timeInRoad.text = durText
            }
            handler.postDelayed(this, 1000)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapUserBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handler.post(runnable)
        onGoClickListener()
        onDeliveryClickListener()
    }

    private fun onGoClickListener() {
        binding.go.setOnClickListener {
            initDialog(StartMapDialogFrag.newInstance(), StartMapDialogFrag.TAG)

        }
    }

    private fun onDeliveryClickListener() {
        binding.delivery.setOnClickListener {
            initDialog(DeliveryFragment.newInstance(), DeliveryFragment.TAG)

        }
    }

    private fun initDialog(dialogFrag: BottomSheetDialogFragment, tag: String) {
        dialogFrag.show(requireActivity().supportFragmentManager, tag)
    }

    companion object {
        const val TAG = "User Map"

        @JvmStatic
        fun newInstance() = UserMapFragment()
    }
}