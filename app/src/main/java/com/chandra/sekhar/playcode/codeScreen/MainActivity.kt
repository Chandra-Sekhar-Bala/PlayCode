package com.chandra.sekhar.playcode.codeScreen

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options
import com.chandra.sekhar.playcode.R
import com.chandra.sekhar.playcode.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private val permissions: Array<String> = arrayOf(
        android.Manifest.permission.READ_EXTERNAL_STORAGE,
        android.Manifest.permission.CAMERA
    )
    private lateinit var bind : ActivityMainBinding
    private lateinit var viewModel: CodeViewModel
    private lateinit var language : Array<String>

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
        val viewModelFactory = CodeViewModelFactory(this)
        viewModel = ViewModelProvider(this@MainActivity, viewModelFactory)[CodeViewModel::class.java]

        // set lifecycle owner
        bind.lifecycleOwner = this

        // Set-up spinner adapter
        language  = this.resources.getStringArray(R.array.Languages)
        bind.langOption.adapter = ArrayAdapter(this, R.layout.spinner_item, language)
    }


    override fun onResume() {
        super.onResume()

        // Spinner click listener is this activity
        bind.langOption.onItemSelectedListener = this

        // on click-listener
        bind.codePicker.setOnClickListener {
            pickAndCropImage()
        }

        viewModel.code.observe(this@MainActivity){
            bind.codeView.setText(it)
        }

    }

    /*
    * Gives option to user to choose between Camera and Gallery +
    * Crop feature the chosen image
    * */
    private fun pickAndCropImage() {
        cropImage.launch(
            options {
                setGuidelines(CropImageView.Guidelines.ON)
            }
        )

    }
    // getting image work done:
    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            // use the returned uri
            val uriContent = result.uriContent
            if (uriContent != null) {
                viewModel.setUri(uriContent)
            }
        } else {
            // an error occurred
            Toast.makeText(this@MainActivity, "No file chosen", Toast.LENGTH_SHORT).show()
        }
    }



    // Check for multiple permission
    private val registerForPermission = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){ it ->
        if(!it.entries.all { it.value }){
            Toast.makeText(this@MainActivity, "Need permission to use this app", Toast.LENGTH_SHORT).show()
            // then shut down
            finish()
        }
    }

    /**
     *
     * Callback method to be invoked when an item in this view has been
     * selected. This callback is invoked only when the newly selected
     * position is different from the previously selected position or if
     * there was no selected item.
     *
     * Implementers can call getItemAtPosition(position) if they need to access the
     * data associated with the selected item.
     *
     * @param parent The AdapterView where the selection happened
     * @param view The view within the AdapterView that was clicked
     * @param position The position of the view in the adapter
     * @param id The row id of the item that is selected
     */
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Log.e("SPINNER","POS: $position")
    }

    /**
     * Callback method to be invoked when the selection disappears from this
     * view. The selection can disappear for instance when touch is activated
     * or when the adapter becomes empty.
     *
     * @param parent The AdapterView that now contains no selected item.
     */
    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}