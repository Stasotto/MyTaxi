package com.example.mytaxi.presentation.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.mytaxi.R
import com.example.mytaxi.databinding.FragmentDriverAuthMakePhotoBinding
import com.example.mytaxi.presentation.viewmodels.DriverViewModel
import com.example.mytaxi.presentation.activities.UserMapsActivity
import com.github.florent37.camerafragment.CameraFragment
import com.github.florent37.camerafragment.configuration.Configuration
import com.github.florent37.camerafragment.listeners.CameraFragmentResultListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException


@SuppressLint("MissingPermission")
class DriverAuthMakePhotoFragment : Fragment() {


    private val PATH = "/storage/self/primary/DCIM/Camera"
    private var file = ""
    private val FILE_PASSPORT = "passport_auth_Taxi"
    private val FILE_DL_FRONT = "dl_f_auth_Taxi"
    private val FILE_DL_BACK = "dl_b_auth_Taxi"

    private lateinit var cameraFragment: CameraFragment
    private var _cameraBinding: FragmentDriverAuthMakePhotoBinding? = null
    private val cameraBinding get() = _cameraBinding!!
    private val driverViewModel: DriverViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        if (driverViewModel.docType.value!!.contains("1") && driverViewModel.driverData.value?.passportPhoto == null) {
            file = FILE_PASSPORT
        }

        if (driverViewModel.docType.value!!.contains("2") && driverViewModel.driverData.value?.licenceFace == null) {
            file = FILE_DL_FRONT
        }

        if (driverViewModel.docType.value!!.contains("3") && driverViewModel.driverData.value?.licenceBack == null) {
            file = FILE_DL_BACK
        }
        _cameraBinding = FragmentDriverAuthMakePhotoBinding.inflate(inflater, container, false)

        return cameraBinding.root
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hasPermissionAndOpenCamera()

        cameraBinding.btnTakePhoto.setOnClickListener {
            takeNewPhoto()
            cameraBinding.cardPhoto.onFinishTemporaryDetach()
            cameraBinding.cardPhoto.visibility = View.INVISIBLE
            cameraBinding.btnTakePhoto.visibility = View.INVISIBLE
            CoroutineScope(Dispatchers.Main).launch {
                cameraBinding.pbPhoto.visibility = View.VISIBLE
                delay(3000)
                Toast.makeText(
                    requireActivity().applicationContext,
                    R.string.toastPhoto,
                    Toast.LENGTH_SHORT
                )
                    .show()
                loadImageFromStorage(PATH)
                cameraBinding.pbPhoto.visibility = View.INVISIBLE
                cameraBinding.imageView.visibility = View.VISIBLE
                cameraBinding.btnRetakePhoto.visibility = View.VISIBLE
                cameraBinding.btnSave.visibility = View.VISIBLE
            }
        }

        cameraBinding.btnRetakePhoto.setOnClickListener {
            startActivityCameraFragment()
            cameraBinding.btnRetakePhoto.visibility = View.INVISIBLE
            cameraBinding.imageView.visibility = View.INVISIBLE
            cameraBinding.cardPhoto.visibility = View.VISIBLE
            cameraBinding.btnSave.visibility = View.INVISIBLE
            CoroutineScope(Dispatchers.Main).launch {
                val file = File(PATH, "$file.jpg")
                file.delete()
                delay(1000)
                cameraBinding.btnTakePhoto.visibility = View.VISIBLE
            }
        }

        cameraBinding.btnSave.setOnClickListener {
            val bitmap = (cameraBinding.imageView.drawable as BitmapDrawable).bitmap
            if (driverViewModel.docType.value!!.contains("1") && file == FILE_PASSPORT) {
                driverViewModel.docType.value += "4"
                driverViewModel.driverData.value!!.passportPhoto = bitmap
            }

            if (driverViewModel.docType.value!!.contains("2") && file == FILE_DL_FRONT) {
                driverViewModel.docType.value += "5"
                driverViewModel.driverData.value!!.licenceFace = bitmap
            }

            if (driverViewModel.docType.value!!.contains("3") && file == FILE_DL_BACK) {
                driverViewModel.docType.value += "6"
                driverViewModel.driverData.value!!.licenceBack = bitmap
            }

            view.findNavController()
                .navigate(R.id.action_driverAuthMakePhotoFragment_to_driverAddPhotosFragment)
        }

    }

    private fun hasPermissionAndOpenCamera() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("!!!","permission granted")
            startActivityCameraFragment()
        } else {
            Log.d("!!!","permission not granted")
            requestPermission()
            hasPermissionAndOpenCamera()

        }
    }

    private fun startActivityCameraFragment() {
        cameraFragment = CameraFragment.newInstance(Configuration.Builder().build())
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(com.example.mytaxi.R.id.content, cameraFragment, "Nothing")
            .commit()
        Log.d("!!!","camera frag started")
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startActivityCameraFragment()

        }
    }

    private fun requestPermission() {
        val permissions = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

        ActivityCompat.requestPermissions(
            requireActivity(),
            permissions,
            PackageManager.PERMISSION_GRANTED
        )
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun takeNewPhoto() {
        cameraFragment.takePhotoOrCaptureVideo(callBackListener, PATH, file)
    }

    private var callBackListener: CameraFragmentResultListener =
        object : CameraFragmentResultListener {

            override fun onVideoRecorded(filePath: String?) {
            }

            override fun onPhotoTaken(bytes: ByteArray?, filePath: String?) {
            }
        }

    private fun loadImageFromStorage(path: String) {
        try {
            val f = File(path, "$file.jpg")
            val b = BitmapFactory.decodeStream(FileInputStream(f))
            val img: ImageView = cameraBinding.imageView
            img.rotation = 90f
            img.setImageBitmap(b)

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _cameraBinding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        val intent = Intent(activity, UserMapsActivity::class.java)
        startActivity(intent)
    }


}
