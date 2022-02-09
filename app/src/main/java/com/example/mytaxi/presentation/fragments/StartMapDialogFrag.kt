package com.example.mytaxi.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mytaxi.R
import com.example.mytaxi.databinding.FragmentStartMapBinding
import com.example.mytaxi.databinding.ItemCarTypeBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class StartMapDialogFrag : BottomSheetDialogFragment() {

    private val binding by viewBinding(FragmentStartMapBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_start_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
    }

    private fun initLayout() {
        val arrayOfCars = arrayOf(
            "Эконом",
            "Комфорт",
            "Комфорт+",
            "Бизнес",
            "Детский",
            "Электро",
            "Быстрый"
        )

        val itemList = (arrayOfCars.indices).map {
            val cardView = view?.findViewById<CardView>(R.id.cardView)
            val view = LayoutInflater.from(context).inflate(R.layout.item_car_type, cardView, false)
            val itemBinding: ItemCarTypeBinding = ItemCarTypeBinding.bind(view)
            itemBinding.root.id = View.generateViewId()
            itemBinding.tvCar.text = arrayOfCars[it]
            itemBinding.root.setOnClickListener {
                itemBinding.viewRound.isVisible = !itemBinding.viewRound.isVisible
            }
            binding.linear.addView(itemBinding.root)
            itemBinding
        }
        binding.flow.referencedIds = itemList.map { it.root.id }.toIntArray()
    }

    companion object {
        const val TAG = "Start Map Dialog"

        @JvmStatic
        fun newInstance() = StartMapDialogFrag()
    }
}