package com.example.mytaxi.presentation.activities

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
import com.example.mytaxi.databinding.FragmentDriverStartAuthBinding

class DriverStartAuthFragment : Fragment() {


    private var _startAuthBinding: FragmentDriverStartAuthBinding? = null
    private val startAuthBinding get() = _startAuthBinding!!
    private val driverLiveData: DriverLiveData by activityViewModels()

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


            val etArr = arrayOf(
                startAuthBinding.etName.text.toString(),
                startAuthBinding.etSurname.text.toString(),
                startAuthBinding.etMail.text.toString(),
                startAuthBinding.etPhone.text.toString(),
                startAuthBinding.etPassword.text.toString(),
                startAuthBinding.etCity.text.toString(),
                startAuthBinding.etBonusCode.text.toString()
            )

            var notEmpty = true
            for (i in etArr.indices) {
                if (etArr[i].isEmpty()) {
                    notEmpty = false
                    break
                }
            }

            if (!notEmpty) {
                Toast.makeText(
                    activity?.applicationContext,
                    "Заполните все поля!",
                    Toast.LENGTH_LONG
                ).show()
            } else {

                val driverData = DriverModel(
                    driverName = startAuthBinding.etName.text.toString(),
                    driverSurname = startAuthBinding.etSurname.text.toString(),
                    driverMail = startAuthBinding.etMail.text.toString(),
                    driverPhone = startAuthBinding.etPhone.text.toString(),
                    driverPass = startAuthBinding.etPassword.text.toString(),
                    driverCity = startAuthBinding.etCity.text.toString(),
                    bonusCode = startAuthBinding.etBonusCode.text.toString(),
                    partnershipVariant = 0,
                    passportPhoto = null,
                    licenceFace = null,
                    licenceBack = null
                )

                driverLiveData.driverData.value = driverData

                view.findNavController()
                    .navigate(R.id.action_driverStartAuthFragment_to_driverAuthPartnershipFragment)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _startAuthBinding = null
    }
}
