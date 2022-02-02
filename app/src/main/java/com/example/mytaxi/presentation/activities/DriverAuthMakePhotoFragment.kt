package com.example.mytaxi.presentation.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
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
import com.example.mytaxi.R
import com.example.mytaxi.databinding.FragmentDriverAuthMakePhotoBinding
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


    private val PATH = "/storage/self/primary"
    private val FILE_NAME = "photo_doc_auth_Taxi"

    private lateinit var cameraFragment: CameraFragment
    private var _cameraBinding: FragmentDriverAuthMakePhotoBinding? = null
    private val cameraBinding get() = _cameraBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
                loadImageFromStorage(PATH)
                cameraBinding.pbPhoto.visibility = View.INVISIBLE
                cameraBinding.imageView.visibility = View.VISIBLE
                cameraBinding.btnRetakePhoto.visibility = View.VISIBLE

            }
        }

        cameraBinding.btnRetakePhoto.setOnClickListener {
            startActivityCameraFragment()
            cameraBinding.btnRetakePhoto.visibility = View.INVISIBLE
            cameraBinding.imageView.visibility = View.INVISIBLE
            cameraBinding.cardPhoto.visibility = View.VISIBLE
            CoroutineScope(Dispatchers.Main).launch {
                val file = File(PATH, "$FILE_NAME.jpg")
                file.delete()
                delay(1000)
                cameraBinding.btnTakePhoto.visibility = View.VISIBLE
            }


        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startActivityCameraFragment()
        }
    }

    private fun hasPermissionAndOpenCamera() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED
        ) {
            startActivityCameraFragment()
        } else {
            requestPermission()
        }
    }

    private fun startActivityCameraFragment() {
        cameraFragment = CameraFragment.newInstance(Configuration.Builder().build())
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(com.example.mytaxi.R.id.content, cameraFragment, "Nothing")
            .commit()
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
            cameraFragment.takePhotoOrCaptureVideo(callBackListener, PATH, FILE_NAME)
    }

    private var callBackListener: CameraFragmentResultListener =
        object : CameraFragmentResultListener {

            override fun onVideoRecorded(filePath: String?) {
            }

            override fun onPhotoTaken(bytes: ByteArray?, filePath: String?) {

                Toast.makeText(
                    requireActivity().applicationContext,
                    R.string.toastPhoto,
                    Toast.LENGTH_SHORT
                )
                    .show()
                //onDestroy()
            }
        }

    private fun loadImageFromStorage(path: String) {
        try {
            val f = File(path, "$FILE_NAME.jpg")
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
        val intent = Intent(activity, DriverMapsActivity::class.java)
        startActivity(intent)
    }


}
