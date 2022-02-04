package com.example.mytaxi.presentation.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.mytaxi.R
import com.example.mytaxi.databinding.FragmentDriverAddPhotosBinding


class DriverAddPhotosFragment : Fragment() {


    private var _addPhotosBinding: FragmentDriverAddPhotosBinding? = null
    private val addPhotosBinding get() = _addPhotosBinding!!
    private val driverLiveData: DriverLiveData by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        driverLiveData.docType.value += "0"
        _addPhotosBinding = FragmentDriverAddPhotosBinding.inflate(inflater, container, false)

        if (driverLiveData.docType.value!!.contains("4")) {
            addPhotosBinding.cbPassport.isChecked = true
        }

        if (driverLiveData.docType.value!!.contains("5")) {
            addPhotosBinding.cbDlFront.isChecked = true
        }

        if (driverLiveData.docType.value!!.contains("6")) {
            addPhotosBinding.cbDlBack.isChecked = true
        }
        return addPhotosBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addPhotosBinding.cvPassport.setOnClickListener {
            driverLiveData.docType.value += "1"
            view.findNavController()
                .navigate(R.id.action_driverAddPhotosFragment_to_driverAuthMakePhotoFragment)
        }

        addPhotosBinding.cvDlFront.setOnClickListener {
            driverLiveData.docType.value += "2"
            view.findNavController()
                .navigate(R.id.action_driverAddPhotosFragment_to_driverAuthMakePhotoFragment)

        }

        addPhotosBinding.cvDlBack.setOnClickListener {
            driverLiveData.docType.value += "3"
            view.findNavController()
                .navigate(R.id.action_driverAddPhotosFragment_to_driverAuthMakePhotoFragment)
        }

        addPhotosBinding.btnDoneReg.setOnClickListener {
            Toast.makeText(
                requireActivity().applicationContext,
                "Спасибо, данные отправлены на проверку!",
                Toast.LENGTH_SHORT
            ).show()
            Log.d("!!!done", driverLiveData.driverData.value.toString())
            val intent = Intent(activity, DriverMapsActivity::class.java)
            startActivity(intent)
            requireActivity().onBackPressed()
        }

        if (
            addPhotosBinding.cbPassport.isChecked &&
            addPhotosBinding.cbDlFront.isChecked &&
            addPhotosBinding.cbDlBack.isChecked
        ) {
            addPhotosBinding.btnDoneReg.visibility = View.VISIBLE
            addPhotosBinding.btnDoneReg.isEnabled = true
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _addPhotosBinding = null
    }

}
