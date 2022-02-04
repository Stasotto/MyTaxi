package com.example.mytaxi.presentation.activities

import android.graphics.Bitmap

data class DriverModel(

    var driverName: String,
    var driverSurname: String,
    var driverMail: String,
    var driverPhone: String,
    var driverPass: String,
    var driverCity: String,
    var bonusCode: String,
    var partnershipVariant: Int = 0,
    var passportPhoto: Bitmap?,
    var licenceFace: Bitmap?,
    var licenceBack: Bitmap?

)
