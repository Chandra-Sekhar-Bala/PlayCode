package com.chandra.sekhar.playcode.codeScreen

import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options
import com.chandra.sekhar.playcode.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private val permissions: Array<String> = arrayOf(
        android.Manifest.permission.READ_EXTERNAL_STORAGE,
        android.Manifest.permission.CAMERA
    )

    private lateinit var bind : ActivityMainBinding
    private lateinit var viewModel: CodeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

        initialize()
    }

    private fun initialize() {
        // check for runtime permission
        registerForPermission.launch(permissions)

        // load viewModel
        viewModel = ViewModelProvider(this@MainActivity)[CodeViewModel::class.java]

        // set lifecycle owner
        bind.lifecycleOwner = this

    }


    override fun onResume() {
        super.onResume()

        // on click-listener
        bind.codePicker.setOnClickListener {
            pickAndCropImage()
        }

    }


    private fun pickAndCropImage() {
        cropImage.launch(
            options {
                setGuidelines(CropImageView.Guidelines.ON)
            }
        )

    }

    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            // use the returned uri
            val uriContent = result.uriContent
            if (uriContent != null) {
                viewModel.setUri(uriContent)
            }
        } else {
            // an error occurred
            Toast.makeText(this@MainActivity, "Error occurred ", Toast.LENGTH_SHORT).show()
        }
    }



    // Check for multiple permission
    private val registerForPermission = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){ it ->
        if(!it.entries.all { it.value }){
            Toast.makeText(this@MainActivity, "Need permission to use this app", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}