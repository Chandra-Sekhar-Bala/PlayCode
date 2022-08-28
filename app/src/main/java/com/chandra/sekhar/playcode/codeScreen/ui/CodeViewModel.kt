package com.chandra.sekhar.playcode.codeScreen.ui

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chandra.sekhar.playcode.codeScreen.repo.network.ApiService
import com.chandra.sekhar.playcode.codeScreen.repo.network.CodeRunnerAPi
import com.chandra.sekhar.playcode.codeScreen.repo.network.OutputModel
import com.chandra.sekhar.playcode.codeScreen.repo.network.ProgramModel
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

enum class ApiStatus{LOADING, ERROR, DONE}
class CodeViewModel(private val context: Context) : ViewModel() {

    private var uri : Uri? = null
    private var _code = MutableLiveData<String>()
    val code : LiveData<String> get() = _code
    /**
     * Code output
     * */
    private var _output = MutableLiveData<OutputModel>()
    val output : LiveData<OutputModel> get() = _output

    /**
     * Status of api
     * **/
    private val _status = MutableLiveData<ApiStatus>()
    val status : LiveData<ApiStatus> get() = _status
//    private var functions = Firebase.functions

    fun setUri(uri: Uri) {
        this.uri = uri
        extractCodeFromImage()
    }

    private fun extractCodeFromImage() {
        val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        viewModelScope.launch {
            try {
                codeFromUri(bitmap)
            }catch (e : Exception){
                Log.e("ERROR", "Exception : "+ e.message)
            }

        }
    }
    /**
     * Fetch code from provided URI
     * **/
    private suspend fun codeFromUri(bitmap: Bitmap) {
        withContext(Dispatchers.IO) {
            // worked bt not so good : Wil use Cloud vision api :P
            val textRecognizer: com.google.mlkit.vision.text.TextRecognizer =
                TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
            val image = InputImage.fromBitmap(bitmap, 0)
            val task: Task<Text> = textRecognizer.process(image)
            task.addOnCompleteListener {
                _code.value = it.result.text
            }.addOnFailureListener{
                Log.e("ERROR", "Exception : "+ it.message)
            }
        }
    }

    /***
     * Call internet and then move data
     * **/

    fun runCode(programModel: ProgramModel){
        viewModelScope.launch {
            runOverCloud(programModel)
        }
    }
    private suspend fun runOverCloud(programModel: ProgramModel){
        _status.value = ApiStatus.LOADING
        try {
            val data = CodeRunnerAPi.retrofitService.runCode(programModel)
            _output.value = data
            _status.value = ApiStatus.DONE
        }catch (e : Exception){
            Log.e("API ERROR", "Error: "+e.message)
            _status.value= ApiStatus.ERROR
        }
    }

}