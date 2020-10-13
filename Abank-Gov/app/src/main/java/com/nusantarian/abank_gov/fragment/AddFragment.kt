package com.nusantarian.abank_gov.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.nusantarian.abank_gov.R
import com.nusantarian.abank_gov.databinding.FragmentAddBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private lateinit var cameraExecutor: ExecutorService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.fab_add_iot)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        startCamera()
        cameraExecutor = Executors.newSingleThreadExecutor()

        return binding.root
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(activity!!)

        cameraProviderFuture.addListener({
            //use to bind lifecycle of camera to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            //preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.cameraScan.surfaceProvider)
                }
            //back camera as default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                //unbind use cases before rebinding
                cameraProvider.unbindAll()

                //bind uses cases to camera
                cameraProvider.bindToLifecycle(
                    activity!!, cameraSelector, preview
                )

            } catch (e: Exception) {
                Log.e("Case Binding Failed", e.toString())
            }

        }, ContextCompat.getMainExecutor(activity!!))
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
}