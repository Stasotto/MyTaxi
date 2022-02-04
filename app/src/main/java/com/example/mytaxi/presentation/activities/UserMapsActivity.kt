package com.example.mytaxi.presentation.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.postDelayed
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mytaxi.R
import com.example.mytaxi.data.models.Route
import com.example.mytaxi.databinding.ActivityMapsBinding
import com.example.mytaxi.presentation.fragments.UserMapFragment
import com.example.mytaxi.presentation.viewmodels.UserMapViewModels
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.maps.android.PolyUtil

class UserMapsActivity : AppCompatActivity(R.layout.activity_maps), OnMapReadyCallback, Runnable{

    private val handler by lazy { Handler() }
    private var mMap: GoogleMap? = null
    private var polylineOptions: PolylineOptions? = null
    private var polylineList = mutableListOf<LatLng>()
    private var currentPosition: MarkerOptions? = null
    private var selectedPosition: MarkerOptions? = null
    private val binding: ActivityMapsBinding by viewBinding()
    private val fusedLocationProviderClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(this)
    }
    private val userMapViewModel: UserMapViewModels by lazy {
        ViewModelProvider(this).get(
            UserMapViewModels::class.java
        )
    }
    private fun openDriverStartAuthFrag() {
        binding.floating.setOnClickListener {
            startActivity(Intent(this, DriverAuthActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPermissions()
        initMap()
        initPlace()
        openDriverStartAuthFrag()
        handler.post(this)

    }

    private fun showDirection() {
        if(UserMapViewModels.b != null) {
            val routeList: List<Route> = UserMapViewModels.b!!.routes
            for (route in routeList) {
                val polyline = route.overViewPolyline.points
                polylineList.addAll(PolyUtil.decode(polyline))
            }

            polylineOptions = PolylineOptions()
            polylineOptions?.color(ContextCompat.getColor(applicationContext, R.color.black))
            polylineOptions?.width(8.0f)
            polylineOptions?.startCap(ButtCap())
            polylineOptions?.jointType(JointType.ROUND)
            polylineOptions?.addAll(polylineList)
            mMap?.addPolyline(polylineOptions!!)

            val builder = LatLngBounds.Builder()
            builder.include(currentPosition?.position!!)
            builder.include(selectedPosition?.position!!)
            mMap?.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 100))
            UserMapViewModels.b = null
        }
        }

    override fun run() {
        showDirection()
        handler.postDelayed(this, 1000)

    }

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0
    }

    private fun getDistance() {
        if(UserMapViewModels.a != null) {
            val alert = AlertDialog.Builder(this)
                .setTitle("Info")
                .setMessage("${UserMapViewModels?.a?.rows?.get(0)?.elements?.get(0)?.distance},${UserMapViewModels?.a?.rows?.get(0)?.elements?.get(0)?.duration}")
                .create()
            alert.show()
        }
    }

    private fun checkPermissions() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )
            return
        }
        val lastLoc = fusedLocationProviderClient.lastLocation

        lastLoc.addOnSuccessListener { loction ->
            if (loction != null) {
                val curLoc = LatLng(loction.latitude, loction.longitude)
                currentPosition = MarkerOptions().position(curLoc)
                showMarker(curLoc)
            }
        }
    }

    private fun initMap() {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun initUserMapFrag() {
        supportFragmentManager.beginTransaction()
            .add(R.id.containerFrag, UserMapFragment.newInstance(), UserMapFragment.TAG)
            .commit()
    }

    private fun initPlace() {
        Places.initialize(applicationContext, getString(R.string.google_maps_key))
        binding.editText.setOnClickListener {
            val fieldList: List<Place.Field> =
                listOf(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME)
            val intent =
                Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).build(this)
            startActivityForResult(intent, 100)
            mMap?.clear()
            polylineList.clear()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            val place = Autocomplete.getPlaceFromIntent(data!!)
            binding.editText.text = Editable.Factory.getInstance().newEditable(place.address)
            selectedPosition = MarkerOptions().position(place.latLng ?: LatLng(1.0, 1.0))
            userMapViewModel.getDirection(
                origin = "${currentPosition?.position?.latitude},${currentPosition?.position?.longitude}",
                destination = "${selectedPosition?.position?.latitude}, ${selectedPosition?.position?.longitude}"
            )
            showDirection()
            getDistance()
            initUserMapFrag()

        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
            Log.d("MyLog", "else")
            val status = Autocomplete.getStatusFromIntent(data!!)
            Toast.makeText(this, status.statusMessage, Toast.LENGTH_SHORT).show()
        }

    }

    private fun showMarker(location: LatLng) {
        mMap?.addMarker(MarkerOptions().position(location))
        mMap?.moveCamera(CameraUpdateFactory.newLatLng(location))
    }

}