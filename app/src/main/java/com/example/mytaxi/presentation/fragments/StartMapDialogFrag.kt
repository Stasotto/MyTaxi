package com.example.mytaxi.presentation.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mytaxi.R
import com.example.mytaxi.databinding.FragmentStartMapBinding
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class StartMapDialogFrag : BottomSheetDialogFragment() {

    private val binding by viewBinding(FragmentStartMapBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start_map, container, false)
        initPlace()
    }

    private fun initPlace() {
        Places.initialize(requireContext().applicationContext, "AIzaSyBql8HcWnoE_0nA1lT3tbs8N4sfC6vokb8")
        binding.editText.setOnClickListener {
            val fieldList: List<Place.Field> = listOf(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME)

            val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).build(requireContext())
            startActivityForResult(intent, 100)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            val place = Autocomplete.getPlaceFromIntent(data!!)
            binding.editText.text = Editable.Factory.getInstance().newEditable(place.address)
            binding.textView.text = place.name
            binding.textView2.text = place.latLng.toString()
        } else  if(resultCode == AutocompleteActivity.RESULT_ERROR) {
            val status = Autocomplete.getStatusFromIntent(data!!)
            Toast.makeText(requireContext(), status.statusMessage, Toast.LENGTH_SHORT).show()
        }

    }

    companion object {
        const val TAG = "Start Map Dialog"
        @JvmStatic
        fun newInstance() = StartMapDialogFrag()
    }
}