package com.chandra.sekhar.playcode.codeScreen

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
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
import java.util.regex.Pattern


class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    //Language Keywords
    private val PATTERN_KEYWORDS: Pattern = Pattern.compile(
        "\\b(abstract|boolean|break|byte|case|catch" +
                "|char|class|continue|default|do|double|else" +
                "|enum|extends|final|finally|float|for|if" +
                "|implements|import|instanceof|int|interface" +
                "|long|native|new|null|package|private|protected" +
                "|public|return|short|static|strictfp|super|switch" +
                "|synchronized|this|throw|transient|try|void|volatile|while)\\b"
    )

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

        // setup code view
        bind.codeView.setEnableLineNumber(true)
        bind.codeView.setLineNumberTextColor(Color.GRAY)
        bind.codeView.setLineNumberTextSize(35f)
        bind.codeView.setEnableAutoIndentation(true)
        bind.codeView.addSyntaxPattern(PATTERN_KEYWORDS, Color.CYAN)
    }


    override fun onResume() {
        super.onResume()

        // Spinner click listener is this activity
        bind.langOption.onItemSelectedListener = this


        viewModel.code.observe(this){
            bind.codeView.setText(it)
        }
        clickListeners()
    }

    private fun clickListeners() {

        // on click-listener
        bind.codePicker.setOnClickListener {
            pickAndCropImage()
        }

        bind.runCode.setOnClickListener{
            compileCode()
        }
        bind.hideOutputScreen.setOnClickListener{
            bind.outputScreen.visibility = View.GONE
        }
    }

    /**
     * Check for code validation and get the data from internet
     */
    private fun compileCode() {
        bind.outputScreen.visibility = View.VISIBLE
        bind.outputScreen.animation = AnimationUtils.loadAnimation(this, R.anim.btu)
    }

    /*
    * Gives option to user to choose between Camera and Gallery +
    * Crop feature the chosen image
    * */
    private fun pickAndCropImage() {
        cropImage.launch(
            options {
                setGuidelines(CropImageView.Guidelines.ON)
            })
    }
    // getting image work done:
    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            // use the returned uri
            result.uriContent?.let { viewModel.setUri(it) }
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

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Log.e("SPINNER","POS: $position")
    }
    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}