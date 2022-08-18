package com.chandra.sekhar.playcode

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.chandra.sekhar.playcode.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private val permissions: Array<String> = arrayOf(
        android.Manifest.permission.READ_EXTERNAL_STORAGE,
        android.Manifest.permission.CAMERA
    )

    private lateinit var bind : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

        // check for runtime permission
        registerForPermission.launch(permissions)
    }


    override fun onResume() {
        super.onResume()

        bind.camera.setOnClickListener{
            getDataFromCamera()
        }
        bind.file.setOnClickListener{
            getDataFromFile()
        }

    }

    private fun getDataFromFile() {
        registerForFile.launch("image/*")
    }
    // Get data for file select:
    private val registerForFile = registerForActivityResult(
        ActivityResultContracts.GetContent()){

//        bind.camera.setImageURI(it)

    }

    private fun getDataFromCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        registerForCamera.launch(intent)
    }

    // Get data for camera click:
    private val registerForCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if(it.resultCode == RESULT_OK && it.data != null){

            val bundle = it.data!!.extras
            val bitmap = bundle?.get("data") as Bitmap

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