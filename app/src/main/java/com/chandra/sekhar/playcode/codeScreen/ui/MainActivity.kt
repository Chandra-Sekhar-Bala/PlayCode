package com.chandra.sekhar.playcode.codeScreen.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
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
import com.chandra.sekhar.playcode.codeScreen.repo.network.ProgramModel
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
    private var position = 0

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
        // Listen to click listeners
        clickListeners()

        // Spinner click listener is this activity
        bind.langOption.onItemSelectedListener = this

        /*
        * ViewModel observers
        * */
        viewModel.code.observe(this){
            bind.codeView.setText(it)
        }

        viewModel.output.observe(this){op->
            compileCode()
            bind.outputText.text = op.output
            bind.memory.text = getString(R.string.time_and_memory, op.cpuTime, op.memory)
        }

        viewModel.status.observe(this){ status->
            compileCode()
            bind.outputProgress.visibility = when(status){
                ApiStatus.LOADING -> View.VISIBLE
                ApiStatus.DONE -> View.GONE
                ApiStatus.ERROR -> View.GONE
            }
        }
    }

    private fun clickListeners() {

        // on click-listener
        bind.codePicker.setOnClickListener {
            pickAndCropImage()
        }
        /*
        * pass data to the ViewModel, call intern and get th work done
        * */
        bind.runCode.setOnClickListener{
            if(bind.codeView.text.isNotEmpty() && position!= 0){
                val model = ProgramModel(bind.codeView.text.toString(), language[position], bind.inputText.text.toString())
                viewModel.runCode(model)
            }else{
                Toast.makeText(this, "Language selection or Code mission", Toast.LENGTH_SHORT).show()
            }
        }

        bind.hideOutputScreen.setOnClickListener{
            bind.outputScreen.visibility = View.GONE
        }
    }

    /**
     * Check for code validation and get the data from internet
     */
    private fun compileCode() {

        if(bind.outputScreen.visibility != View.VISIBLE){
            bind.outputScreen.visibility = View.VISIBLE
            bind.outputScreen.animation = AnimationUtils.loadAnimation(this, R.anim.btu)
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
        this.position = position
    }
    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}